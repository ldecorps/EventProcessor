package decorps.eventprocessor.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class SetEncodersAndLedRingRuleTest {
	SetEncodersAndLedIndicatorsRule cut = new SetEncodersAndLedIndicatorsRule();
	EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
			.build(DsiTetraMapTest.sampleEditbufferProgramDataDump);

	@Test
	public void itSpitsOut_OneSetAllIndicator_and_OneSetAllEncoderValues_sysex()
			throws Exception {
		BankLayout.programParameterData = ProgramParameterData
				.build(DsiTetraMapTest.sampleEditbufferProgramDataDump
						.getMessage());
		MapRepository.initialiseCurrentBank();
		EventProcessorMidiMessage result = cut
				.transform(eventProcessorMidiMessage);
		assertThat(result, instanceOf(EventProcessorMidiMessageComposite.class));
		assertEquals(
				2,
				((EventProcessorMidiMessageComposite) result).eventProcessorMidiMessages
						.size());
	}
}
