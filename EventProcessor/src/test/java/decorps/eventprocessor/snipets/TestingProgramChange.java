package decorps.eventprocessor.snipets;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class TestingProgramChange {

	public static void main(String[] args) {
		EventProcessor eventProcessor = new EventProcessor();
		// ProgramEditBufferDumpRequest
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, eventProcessor.fromLocalToTetra);

		synchronized (EventProcessorMidiMessage.wait) {
			try {
				EventProcessorMidiMessage.wait.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
	}

}
