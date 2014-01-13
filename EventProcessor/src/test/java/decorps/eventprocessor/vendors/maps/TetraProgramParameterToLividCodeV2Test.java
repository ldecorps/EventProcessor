package decorps.eventprocessor.vendors.maps;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new TetraProgramParameterToLividCodeV2();

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;

	@Test
	public void canMapOscillator1Frequency() throws Exception {
		LividCodeEventProcessorCCShortMessage forCodeV2 = cut
				.map(sampleProgramParameterData.A.oscillator1Frequency);
		assertEquals(0, forCodeV2.shortMessage.getChannel());
		assertEquals(0, forCodeV2.shortMessage.getData1());
		assertEquals(25, forCodeV2.shortMessage.getData2());
	}

}
