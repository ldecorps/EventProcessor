package decorps.eventprocessor.snipets;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.Transpose;
import decorps.eventprocessor.vendors.dsi.MessageType;

public class TestingTranspose {

	public static void main(String[] args) throws InterruptedException {
		EventProcessor eventProcessor = EventProcessor.getInstance();
		eventProcessor.registerAction(new Transpose(-12),
				MessageType.ANY_MESSAGE, eventProcessor.fromTetraToLivid);
		synchronized (EventProcessorMidiMessage.wait) {
			EventProcessorMidiMessage.wait.wait();
		}
	}

}
