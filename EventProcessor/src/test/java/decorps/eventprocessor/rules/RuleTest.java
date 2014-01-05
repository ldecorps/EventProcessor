package decorps.eventprocessor.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class RuleTest {
	final EventProcessor eventProcessor = EventProcessor.build();

	@Test
	public void canRegisterARuleToAProgramChange() throws Exception {
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange);
		assertThat(eventProcessor.getActions(), not(empty()));
	}
}
