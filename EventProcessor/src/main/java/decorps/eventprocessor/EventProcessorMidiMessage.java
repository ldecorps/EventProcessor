package decorps.eventprocessor;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.utils.DumpReceiver;

public abstract class EventProcessorMidiMessage extends MidiMessage {

	protected static DsiTetraMap dsiTetraMap = new DsiTetraMap();
	public static Object wait = new Object();

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
		ShortMessage toSend = this.getAsShortMessage().shortMessage;
		System.out.println("sending: " + DumpReceiver.decodeMessage(toSend)
				+ " with timestamp " + timestamp + " to receiver "
				+ receiver.toString());
		receiver.send(toSend, timestamp);
		synchronized (wait) {
			wait.notify();
		}
	}
}
