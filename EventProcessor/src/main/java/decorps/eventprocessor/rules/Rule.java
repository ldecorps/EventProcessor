package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public interface Rule {

	EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProcessorMidiMessage);

}
