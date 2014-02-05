package decorps.eventprocessor.snipets;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.exceptions.EventProcessorException;

public class MakingRingCycle {

	public static void main(String[] args) throws InterruptedException {
		EventProcessor eventProcessor = EventProcessor.getInstance();

		ShortMessage shortMessage = new ShortMessage();
		for (byte i = 0; i < 128; i++) {
			try {
				shortMessage.setMessage(ShortMessage.CONTROL_CHANGE, 0, 33, i);
			} catch (InvalidMidiDataException e1) {
				e1.printStackTrace();
				throw new EventProcessorException(e1);
			}

			eventProcessor.fromTetraToLivid.receiver.send(shortMessage, -1);
			Thread.sleep(500);
		}

	}

}
