package decorps.eventprocessor.rules;

import static decorps.eventprocessor.vendors.livid.messages.Set_LED_Ring_Style.*;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class InitialiseLEDRingStyleTest {
	@Test
	public void initialiseFirstBankBasedOnMapping() throws Exception {
		EventProcessor eventProcessor = EventProcessor.getInstance();

		EventProcessorMidiMessage initialiseLEDRingStyle = LividMessageFactory
				.buildLED_Ring_Style(WALK, EQ, FILL, SPREAD);
		eventProcessor.fromTetraToLivid.receiver.send(initialiseLEDRingStyle,
				-1);
	}

}
