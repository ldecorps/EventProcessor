package decorps.eventprocessor.snipets;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.Transpose;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class TestingTranspose {

	public static void main(String[] args) throws InterruptedException {
		EventProcessor eventProcessor = new EventProcessor();
		eventProcessor.registerAction(new Transpose(-12),
				TetraParameter.ANY_MESSAGE, eventProcessor.fromTetraToLivid);
		synchronized (EventProcessorMidiMessage.wait) {
			EventProcessorMidiMessage.wait.wait();
		}
	}

}
