package decorps.eventprocessor.rules;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.EventProcessorShortMessage;

public class ProgramDumpRequestRule implements Rule {

	@Override
	public EventProcessorShortMessage transform(
			EventProcessorShortMessage eventProvessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
