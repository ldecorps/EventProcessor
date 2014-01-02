package decorps.eventprocessor.rules;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.EventProcessorMidiMessage;

public class ProgramDumpRequestRule implements Rule, ToTetra {

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
