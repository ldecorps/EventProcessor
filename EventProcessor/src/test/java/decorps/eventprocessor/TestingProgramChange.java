package decorps.eventprocessor;

import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;

public class TestingProgramChange {

	public static void main(String[] args) {
		EventProcessor eventProcessor = new EventProcessor();
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, eventProcessor.fromTetraToTetra);

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
