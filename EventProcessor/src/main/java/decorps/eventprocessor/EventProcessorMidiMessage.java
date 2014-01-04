package decorps.eventprocessor;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.utils.BaseUtils;

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
		RuntimeException e = new ClassCastException(
				"not a EventProcessorShortMessage");
		e.printStackTrace();
		throw e;
	}

	public void send(Receiver receiver, long timestamp) {
		System.out.println("Sending... " + BaseUtils.logoutMidiMessage(this));
		receiver.send(this, timestamp);
		synchronized (wait) {
			wait.notify();
		}
	}

	public EventProcessorSysexMessage getAsSysexMessage() {
		if (this instanceof EventProcessorSysexMessage)
			return (EventProcessorSysexMessage) this;
		throw new ClassCastException("not a EventProcessorSysexMessage");
	}

	public boolean isSysexMessage() {
		return this instanceof EventProcessorSysexMessage;
	}

	public boolean isShortMessage() {
		return this instanceof EventProcessorShortMessage;
	}
}