package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class Echo implements Rule {

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		return eventProvessorMidiMessage;
	}

	public static Echo build() {
		return new Echo();
	}
}
