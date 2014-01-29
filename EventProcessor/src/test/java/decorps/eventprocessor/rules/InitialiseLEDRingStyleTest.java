package decorps.eventprocessor.rules;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class InitialiseLEDRingStyleTest {
	@Test
	public void initialiseFirstBankBasedOnMapping() throws Exception {
		EventProcessor eventProcessor = EventProcessor.build();

		EventProcessorMidiMessage initialiseLEDRingStyle = LividMessageFactory
				.buildLED_Ring_Style(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
						3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2,
						2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
		eventProcessor.fromTetraToLivid.receiver.send(initialiseLEDRingStyle,
				-1);
	}
}
