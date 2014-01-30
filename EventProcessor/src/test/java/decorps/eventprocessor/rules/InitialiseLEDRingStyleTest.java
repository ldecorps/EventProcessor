package decorps.eventprocessor.rules;

import static decorps.eventprocessor.vendors.livid.messages.Set_LED_Ring_Style.*;

import org.junit.Ignore;
import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class InitialiseLEDRingStyleTest {
	@Test
	public void initialiseFirstBankBasedOnMapping() throws Exception {
		EventProcessor eventProcessor = EventProcessor.build();

		EventProcessorMidiMessage initialiseLEDRingStyle = LividMessageFactory
				.buildLED_Ring_Style(WALK, EQ, FILL, SPREAD);
		eventProcessor.fromTetraToLivid.receiver.send(initialiseLEDRingStyle,
				-1);
	}

	@Test
	@Ignore
	// TODO should query all encoders values, style, and LED ring style
	// and do that for each of the 4 banks
	public void shouldQueryTheCurrentBankForItsParametersRingStyle()
			throws Exception {
		throw new EventProcessorException("Not Implemented Yet");
	}
}
