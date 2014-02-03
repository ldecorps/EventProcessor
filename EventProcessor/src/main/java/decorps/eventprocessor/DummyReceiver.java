package decorps.eventprocessor;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.exceptions.EventProcessorException;

public class DummyReceiver implements Receiver {

	private MidiMessage sentMidiMessage;
	public static Object wait = new Object();

	public MidiMessage getSentMidiMessage() {
		return sentMidiMessage;
	}

	public void send(MidiMessage message, long timeStamp) {
		this.sentMidiMessage = message;
		synchronized (wait) {
			wait.notifyAll();
		}
	}

	public void close() {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
