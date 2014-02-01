package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameterTest;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.Encoder;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new TetraProgramParameterToLividCodeV2();

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;

	@Before
	public void initialiseLayout() {
		BankLayout.CurrentBank = new BankLayout(1);
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
		EventProcessorMap map = getTestingMap();

		final AbstractProgramParameter abstractProgramParameter = map
				.getAbstractProgramParameter();
		final List<Controller> controllersForParameter = MapRepository
				.getControllersForParameter(abstractProgramParameter);

		assertThat(controllersForParameter.size(), is(2));
	}

	public EventProcessorMap getTestingMap() {
		return new Oscillator1Glide_to_E0B1(
				AbstractProgramParameterTest.newSampleAbsoluteParameter(),
				BankLayout.CurrentBank.encoders[0],
				BankLayout.CurrentBank.buttons[1]);
	}

	@Test
	public void mapOneOfItsControllersToItsAbstractProgramParameter()
			throws Exception {
		EventProcessorMap map = getTestingMap();
		final Controller controller = map.getControllers().get(0);
		final byte newControllerValue = AbstractProgramParameterTest
				.getRandomByte();
		controller.setValue(newControllerValue);

		map.refreshProgramParameter();

		assertEquals(newControllerValue, map.getAbstractProgramParameter()
				.getValue());
	}

	@Test
	public void mapItsAbstractProgramParameterToItsControllers()
			throws Exception {
		EventProcessorMap map = getTestingMap();
		final byte newParameterValue = AbstractProgramParameterTest
				.getRandomByte();
		map.getAbstractProgramParameter().setValue(newParameterValue);
		map.refreshControllers();

		assertEquals(newParameterValue, map.getControllers().get(0).getValue());
	}

}
