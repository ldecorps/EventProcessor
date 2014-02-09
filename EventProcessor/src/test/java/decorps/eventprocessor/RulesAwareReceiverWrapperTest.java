package decorps.eventprocessor;

import static decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest.getRandomByte;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorShortMessage;

public class RulesAwareReceiverWrapperTest {

	RulesAwareReceiverWrapper cut = new RulesAwareReceiverWrapper(null, null);

	@Test
	// FIXEME
	@Ignore
	public void shouldPassOnSystemMessages() throws Exception {
		EventProcessorShortMessage systemMessage = (EventProcessorShortMessage) EventProcessorShortMessage
				.buildShortMessage((byte) 0xF0 & 0xF7, 0x7f, getRandomByte(),
						getRandomByte());

		assertTrue(cut.shouldFilter(systemMessage));

	}
}
