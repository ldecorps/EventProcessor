package decorps.eventprocessor.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public class SetRingIndicatorsViaCCsRuleTest {
	EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
			.build(DsiTetraMapTest.sampleEditbufferProgramDataDump);
	SetRingIndicatorsViaCCsRule cut = new SetRingIndicatorsViaCCsRule();

	@Test
	public void transformProgramParameterDataIntoCcs() throws Exception {
		EventProcessorMidiMessage result = cut
				.transform(eventProcessorMidiMessage);
		assertThat(result, instanceOf(EventProcessorMidiMessageComposite.class));

		for (EventProcessorMidiMessage cc : result.getAsComposite().eventProcessorMidiMessages) {
			final LividCodeEventProcessorCCShortMessage currentCc = (LividCodeEventProcessorCCShortMessage) cc;
			final AbstractProgramParameter abstractProgramParameter = currentCc.abstractProgramParameter;
			assertEquals(abstractProgramParameter + " not mapped correctly",
					abstractProgramParameter.getRebasedValue(), (byte) cc
							.getAsShortMessage().getData2());
		}
	}
}
