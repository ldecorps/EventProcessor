package decorps.eventprocessor;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class TestingProgramChange {

	public static void main(String[] args) {
		EventProcessor eventProcessor = new EventProcessor();
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, eventProcessor.fromTetraToTetra);

		synchronized (EventProcessorMidiMessage.wait) {
			try {
				// EventProcessorMidiMessage.wait.wait();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
	}

}
