package decorps.eventprocessor.vendors.maps;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;
import decorps.eventprocessor.vendors.livid.LividCodev2Map;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new TetraProgramParameterToLividCodeV2();

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;

	@Test
	public void canMapOscillator1Frequency() throws Exception {
		LividCodeEventProcessorCCShortMessage forCodeV2 = cut
				.map(sampleProgramParameterData.A.oscillator1Frequency);
		checkOscillator1Frequency(forCodeV2);
	}

	public void checkOscillator1Frequency(
			LividCodeEventProcessorCCShortMessage forCodeV2) {
		assertEquals(0, forCodeV2.shortMessage.getChannel());
		assertEquals(0, forCodeV2.shortMessage.getData1());
		assertEquals(25, forCodeV2.shortMessage.getData2());
	}

	@Test
	public void canMapWholeProgramParameterData() throws Exception {
		LividCodeEventProcessorCCShortMessageComposite allCcShortMessages = cut
				.mapToCcs(sampleProgramParameterData);
		checkOscillator1Frequency(allCcShortMessages
				.getLividCodeEventProcessorCCShortMessageList().get(0));
	}

	@Test
	public void mapProgramParameterData_to_SetAllLedIndicators()
			throws Exception {
		EventProcessorMidiMessage result = getCutAsTetraToLividMap()
				.mapToSetAllLedIndicators(sampleProgramParameterData);
		assertArrayEquals(LividCodev2Map.buildSet_all_LED_indicators(),
				result.getMessage());
	}

	public TetraProgramParameterToLividCodeV2 getCutAsTetraToLividMap() {
		return (TetraProgramParameterToLividCodeV2) cut;
	}

	@Test
	public void mapProgramParameterData_to_SetLedRingIndicators()
			throws Exception {
		EventProcessorMidiMessage result = getCutAsTetraToLividMap()
				.mapToSetLedRingsIndicators(sampleProgramParameterData);
		assertArrayEquals(LividCodev2Map.buildSet_LED_Ring_indicators(17),
				result.getMessage());
	}
}
