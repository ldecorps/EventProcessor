package decorps.eventprocessor;

import javax.sound.midi.MidiMessage;

public abstract class EventProcessorMidiMessage extends MidiMessage {

	protected EventProcessorMidiMessage(byte[] data) {
		super(data);
	}

}
