package decorps.eventprocessor.snipets;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.RulesAwareReceiverWrapper;
import decorps.eventprocessor.exceptions.EventProcessorException;

public class TestingSendingNote {

	public static void main(String[] args) {
		EventProcessor eventProcessor = new EventProcessor();

		ShortMessage shortMessage = new ShortMessage();
		try {
			shortMessage.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
			throw new EventProcessorException(e1);
		}

		eventProcessor.fromTetraToLivid.receiver.send(shortMessage, -1);

		synchronized (RulesAwareReceiverWrapper.wait) {
			try {
				RulesAwareReceiverWrapper.wait.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
	}

}
