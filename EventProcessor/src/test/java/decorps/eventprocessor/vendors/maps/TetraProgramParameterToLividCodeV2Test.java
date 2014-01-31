package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Glide;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.Encoder;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new TetraProgramParameterToLividCodeV2();

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;
	byte[] bytes = new byte[1];

	@Before
	public void initialiseLayout() {
		BankLayout.CurrentBank = new BankLayout(1);
		new Random().nextBytes(bytes);
	}

	private void checkOscillator1Frequency(EventProcessorMidiMessage forCodeV2) {
		assertEquals("channel", 0, forCodeV2.getAsShortMessage().getChannel());
		assertEquals("ledring", 1, forCodeV2.getAsShortMessage().getData1());
		assertEquals("value", 25, forCodeV2.getAsShortMessage().getData2());
	}

	@Test
	public void canMapWholeProgramParameterData() throws Exception {
		EventProcessorMidiMessage allCcShortMessages = cut
				.mapToCcs(sampleProgramParameterData);
		checkOscillator1Frequency(allCcShortMessages.getAsComposite()
				.getMessages().get(0));
	}

	public TetraProgramParameterToLividCodeV2 getCutAsTetraToLividMap() {
		return (TetraProgramParameterToLividCodeV2) cut;
	}

	@Test
	public void applyTheMappingToEncodersAndButtons_OnStartUp()
			throws Exception {
		cut.applyMapping();
		boolean oneEncoderHasOneProgramParameter = false;
		for (Encoder encoder : BankLayout.CurrentBank.encoders) {
			if (null != encoder.getProgramParameter()) {
				oneEncoderHasOneProgramParameter = true;
				break;
			}
		}
		assertTrue(oneEncoderHasOneProgramParameter);
	}

	@Test
	public void oneMappingAssociateOneParameterToManyControllers()
			throws Exception {
		getTestingMap();

		assertThat(
				MapRepository.getControllersForParameter(new Oscillator1Glide(
						0, (byte) 0)),
				hasItems(BankLayout.CurrentBank.encoders[0],
						BankLayout.CurrentBank.buttons[1]));
	}

	public EventProcessorMap getTestingMap() {
		return new Oscillator1Glide_to_E0B1(new Oscillator1Glide(0, bytes[0]),
				BankLayout.CurrentBank.encoders[0],
				BankLayout.CurrentBank.buttons[1]);
	}

	@Test
	public void mapOneOfItsControllersToItsAbstractProgramParameter()
			throws Exception {
		EventProcessorMap map = getTestingMap();
		final Controller controller = map.getControllers().get(0);
		controller.setValue(bytes[0]);

		map.map(controller);

		assertEquals(bytes[0], map.getAbstractProgramParameter().getValue());
	}

	@Test
	public void mapItsAbstractProgramParameterToItsControllers()
			throws Exception {
		throw new EventProcessorException("Not Implemented Yet");
	}
}
