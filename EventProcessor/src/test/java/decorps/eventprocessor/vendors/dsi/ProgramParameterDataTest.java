package decorps.eventprocessor.vendors.dsi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;

import org.junit.Test;

import decorps.eventprocessor.EventProcessorTest;

public class ProgramParameterDataTest {

	public final static byte[] unpacked = ProgramDataDump
			.buildProgramDump(EventProcessorTest.sampleProgramDataDump
					.getData()).programParameterData.data;
	public final static ProgramParameterData sampleProgramParameterData = ProgramParameterData
			.build(unpacked);

	@Test
	public void storesParamForBothLayersByDefault() throws Exception {
		assertThat(sampleProgramParameterData.A.oscillator1Frequency,
				hasToString(sampleProgramParameterData.B.oscillator1Frequency.toString()));
	}
}