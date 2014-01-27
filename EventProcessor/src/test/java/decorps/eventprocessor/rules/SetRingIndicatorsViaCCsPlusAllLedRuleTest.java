package decorps.eventprocessor.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public class SetRingIndicatorsViaCCsPlusAllLedRuleTest {
	EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
			.build(DsiTetraMapTest.sampleEditbyfferProgramDataDump);
	SetRingIndicatorsViaCCsPlusAllLedRule cut = new SetRingIndicatorsViaCCsPlusAllLedRule();

	@Test
	public void transformProgramParameterDataIntoCcs() throws Exception {
		EventProcessorMidiMessage result = cut
				.transform(eventProcessorMidiMessage);
		assertThat(result, instanceOf(EventProcessorMidiMessageComposite.class));
		assertThat(result.getAsComposite().eventProcessorMidiMessages,
				hasSize(2));
		final EventProcessorMidiMessage ccs = result.getAsComposite().eventProcessorMidiMessages
				.get(1);
		assertThat(ccs, instanceOf(EventProcessorMidiMessageComposite.class));
		assertThat(ccs.getAsComposite().eventProcessorMidiMessages,
				not(empty()));
		for (EventProcessorMidiMessage cc : ccs.getAsComposite().eventProcessorMidiMessages) {
			final LividCodeEventProcessorCCShortMessage currentCc = (LividCodeEventProcessorCCShortMessage) cc;
			System.out.println(currentCc);
			final AbstractProgramParameter abstractProgramParameter = currentCc.abstractProgramParameter;
			assertEquals(abstractProgramParameter + " not mapped correctly",
					abstractProgramParameter.getRebasedValue(), (byte) cc
							.getAsShortMessage().getData2());
		}
	}
}
