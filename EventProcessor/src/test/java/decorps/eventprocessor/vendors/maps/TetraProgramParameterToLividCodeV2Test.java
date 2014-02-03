package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerTest;
import decorps.eventprocessor.vendors.livid.Encoder;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new DefaultControllerParameterMap(
			ProgramParameterTest.newSampleAbsoluteParameter(),
			ControllerTest.newAbsoluteEncoderController());

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;

	@Before
	public void initialiseLayout() {
		BankLayout.CurrentBank = new BankLayout(1);
	}

	private void checkOscillator1Frequency(
			Controller newAbsoluteEncoderController,
			ProgramParameter newSampleAbsoluteParameter,
			EventProcessorShortMessage eventProcessorShortMessage) {
		assertEquals("channel", 0, eventProcessorShortMessage.getChannel());
		assertEquals("ledring", newAbsoluteEncoderController.getCCNumber(),
				eventProcessorShortMessage.getData1());
		assertEquals("value", newSampleAbsoluteParameter.getRebasedValue(),
				eventProcessorShortMessage.getData2());
	}

	@Test
	public void canMapOneParameter() throws Exception {
		final ProgramParameter newSampleAbsoluteParameter = ProgramParameterTest
				.newSampleAbsoluteParameter();
		final Controller newAbsoluteEncoderController = ControllerTest
				.newAbsoluteEncoderController();
		cut = new DefaultControllerParameterMap(newSampleAbsoluteParameter,
				newAbsoluteEncoderController);
		EventProcessorMidiMessage ccForLivid = cut.mapToLividCc();
		checkOscillator1Frequency(newAbsoluteEncoderController,
				newSampleAbsoluteParameter, ccForLivid.getAsShortMessage());
	}

	public TetraProgramParameterToLividCodeV2 getCutAsTetraToLividMap() {
		return (TetraProgramParameterToLividCodeV2) cut;
	}

	@Test
	public void applyTheMappingToEncodersAndButtons_OnStartUp()
			throws Exception {
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
		MapRepository.maps.clear();
		EventProcessorMap map = getTestingMap();

		final ProgramParameter programParameter = map.getProgramParameter();
		final List<Controller> controllersForParameter = MapRepository
				.getControllersForParameter(programParameter);

		assertThat(
				controllersForParameter,
				hasItems(BankLayout.CurrentBank.encoders[0],
						BankLayout.CurrentBank.buttons[1]));
	}

	public EventProcessorMap getTestingMap() {
		return new Oscillator1Glide_to_E0B1(
				ProgramParameterTest.newSampleAbsoluteParameter(),
				BankLayout.CurrentBank.encoders[0],
				BankLayout.CurrentBank.buttons[1]);
	}

	@Test
	public void mapOneOfItsControllersToItsAbstractProgramParameter()
			throws Exception {
		EventProcessorMap map = getTestingMap();
		final Controller controller = map.getControllers().get(0);
		final byte newControllerValue = ProgramParameterTest
				.getRandomByteOtherThan(controller.getValue());
		controller.setValue(newControllerValue);

		map.refreshProgramParameter();

		assertEquals(newControllerValue, map.getProgramParameter().getValue());
	}

	@Test
	public void mapItsAbstractProgramParameterToItsControllers()
			throws Exception {
		EventProcessorMap map = getTestingMap();
		final byte newParameterValue = ProgramParameterTest
				.getRandomByteOtherThan(map.getProgramParameter().getValue());
		map.getProgramParameter().setValue(newParameterValue);

		map.refreshControllers();

		assertEquals(newParameterValue, map.getControllers().get(0).getValue());
	}
}
