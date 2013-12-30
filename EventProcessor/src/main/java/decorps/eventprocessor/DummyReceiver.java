package decorps.eventprocessor;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class DummyReceiver implements Receiver {

	private MidiMessage sentMidiMessage;

	public MidiMessage getSentMidiMessage() {
		return sentMidiMessage;
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		this.sentMidiMessage = message;
	}

	@Override
	public void close() {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
