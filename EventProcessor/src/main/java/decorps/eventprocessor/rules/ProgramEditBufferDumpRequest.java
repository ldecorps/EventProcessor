package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;

public class ProgramEditBufferDumpRequest implements Rule {

	public static final EventProcessorMidiMessage programEditBufferDumpRequest = DsiMessageFactory
			.buildProgramEditBufferDumpRequest();

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		EventProcessorMidiMessage result = programEditBufferDumpRequest;
		return result;
	}

	public static ProgramEditBufferDumpRequest build() {
		return new ProgramEditBufferDumpRequest();
	}
}
