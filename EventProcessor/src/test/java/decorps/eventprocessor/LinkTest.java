package decorps.eventprocessor;

import org.junit.Test;

import decorps.eventprocessor.dsi.TetraParameters;
import decorps.eventprocessor.rules.ProgramDumpRequestRule;

public class LinkTest {

	final private Link cut = EventProcessor.build().link;

	@Test
	public void canRegisterARuleToAProgramChange() throws Exception {
		cut.register(new ProgramDumpRequestRule(),
				TetraParameters.ProgramChange);
	}
}
