package decorps.eventprocessor.rules;

import static decorps.eventprocessor.dsi.DsiTetraMap.DSI_ID;
import static decorps.eventprocessor.dsi.DsiTetraMap.End_Of_Exclusive;
import static decorps.eventprocessor.dsi.DsiTetraMap.Request_Program_Transmit;
import static decorps.eventprocessor.dsi.DsiTetraMap.System_Exclusive;
import static decorps.eventprocessor.dsi.DsiTetraMap.Tetra_ID;
import static decorps.eventprocessor.dsi.DsiTetraMap.buildBankNumber;
import static decorps.eventprocessor.dsi.DsiTetraMap.buildProgramNumber;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import decorps.eventprocessor.EventProcessorSysexMessage;

public class ProgramDumpRequestRuleTest {
	final byte BANK_ONE = 0;
	final byte PROGRAM_ONE = 0;

	final ProgramDumpRequestRule programDumpRequestRule = ProgramDumpRequestRule
			.build();

	@Test
	public void canBuildProgramDumpRequest() {
		assertThat(EventProcessorSysexMessage.build(System_Exclusive, DSI_ID,
				Tetra_ID, Request_Program_Transmit, buildBankNumber(BANK_ONE),
				buildProgramNumber(PROGRAM_ONE), End_Of_Exclusive),
				notNullValue());
	}
}
