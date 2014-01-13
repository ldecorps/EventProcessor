package decorps.eventprocessor.vendors.dsi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;

import org.junit.Test;

import decorps.eventprocessor.EventProcessorTest;

public class ProgramParameterDataTest {

	final byte[] unpacked = ProgramDataDump.buildProgramDump(EventProcessorTest
			.getSampleProgramDataDumpSysexMessage().getData()).programParameterData.data;

	final ProgramParameterData cut = ProgramParameterData.build(unpacked);

	@Test
	public void storesParamForBothLayersByDefault() throws Exception {
		assertThat(cut.A.oscillator1Frequency,
				hasToString(cut.B.oscillator1Frequency.toString()));
	}
}
