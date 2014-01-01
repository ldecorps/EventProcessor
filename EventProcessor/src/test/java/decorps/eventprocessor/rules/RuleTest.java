package decorps.eventprocessor.rules;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.Link;
import decorps.eventprocessor.dsi.TetraParameters;

public class RuleTest {
	final Link cut = EventProcessor.build().link;

	@Test
	public void canRegisterARuleToAProgramChange() throws Exception {
		cut.register(new ProgramDumpRequestRule(),
				TetraParameters.ProgramChange);
	}
}
