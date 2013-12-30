package decorps.eventprocessor.rules;

import decorps.eventprocessor.EventProcessorShortMessage;

public interface Rule {

	EventProcessorShortMessage transform(
			EventProcessorShortMessage eventProvessorMidiMessage);

}
