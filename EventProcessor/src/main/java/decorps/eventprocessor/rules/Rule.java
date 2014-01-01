package decorps.eventprocessor.rules;

import decorps.eventprocessor.EventProcessorMidiMessage;

public interface Rule {

	EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage);

}
