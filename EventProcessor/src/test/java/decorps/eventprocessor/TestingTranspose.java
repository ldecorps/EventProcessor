package decorps.eventprocessor;

import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.Transpose;

public class TestingTranspose {

	public static void main(String[] args) throws InterruptedException {
		EventProcessor eventProcessor = new EventProcessor();
		eventProcessor.registerAction(new Transpose(-12),
				TetraParameter.ANY_MESSAGE, eventProcessor.fromKeyboardToTetra);
		synchronized (EventProcessorMidiMessage.wait) {
			EventProcessorMidiMessage.wait.wait();
		}
	}

}
