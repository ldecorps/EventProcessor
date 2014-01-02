package decorps.eventprocessor;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.dsi.TetraParameter;

public abstract class EventProcessorMidiMessage extends MidiMessage {

	protected static DsiTetraMap dsiTetraMap = new DsiTetraMap();

	protected EventProcessorMidiMessage(byte[] data) {
		super(data);
	}

	public boolean is(TetraParameter tetraParameter) {
		return tetraParameter.is(this);
	}

	public EventProcessorShortMessage getAsShortMessage() {
		if (this instanceof EventProcessorShortMessage)
			return (EventProcessorShortMessage) this;
		throw new ClassCastException("not a EventProcessorShortMessage");
	}

	public void send(Receiver receiver, long timestamp) {
		receiver.send(this, timestamp);
	}
}
