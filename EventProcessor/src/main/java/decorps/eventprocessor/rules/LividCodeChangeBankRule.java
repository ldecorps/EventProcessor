package decorps.eventprocessor.rules;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class LividCodeChangeBankRule implements Rule {

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
