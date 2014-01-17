package decorps.eventprocessor.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import javax.sound.midi.SysexMessage;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;

public class SetLedAndLedRingIndicatorsRuleTest {
	SetLedAndLedRingIndicatorsRule cut = new SetLedAndLedRingIndicatorsRule();
	SysexMessage sampleProgramDataDump = DsiTetraMapTest.sampleProgramDataDump;
	EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
			.build(sampleProgramDataDump);

	@Test
	public void itSpitsOut_OneSetAllIndicator_and_OneSetRingIndicator_sysex()
			throws Exception {
		EventProcessorMidiMessage result = cut
				.transform(eventProcessorMidiMessage);
		assertThat(result, instanceOf(EventProcessorMidiMessageComposite.class));
		assertEquals(
				2,
				((EventProcessorMidiMessageComposite) result).eventProcessorMidiMessages.length);
	}

}
