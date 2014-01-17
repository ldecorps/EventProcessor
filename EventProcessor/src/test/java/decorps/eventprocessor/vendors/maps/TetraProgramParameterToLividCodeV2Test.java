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
		for (LividCodeEventProcessorCCShortMessage forCodeV2 : allCcShortMessages
				.getLividCodeEventProcessorCCShortMessageList()) {
			checkOscillator1Frequency(forCodeV2);
		}
	}

	@Test
	public void mapProgramParameterData_to_SetAllLedIndicators()
			throws Exception {
		TetraProgramParameterToLividCodeV2 map = new TetraProgramParameterToLividCodeV2();
		EventProcessorMidiMessage result = map
				.mapToSetAllLedIndicators(sampleProgramParameterData);
		assertArrayEquals(LividCodev2Map.buildSet_all_LED_indicators(0x1),
				result.getMessage());

	}
}
