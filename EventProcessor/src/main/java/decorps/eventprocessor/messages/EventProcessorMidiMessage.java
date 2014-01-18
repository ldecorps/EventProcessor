package decorps.eventprocessor.messages;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.utils.BaseUtils;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

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
		System.out.println("Sending... "
				+ BaseUtils.decodeMessage(getMidiMessage())
				+ " with timestamp " + timestamp);
		receiver.send(getMidiMessage(), timestamp);
		synchronized (wait) {
			wait.notify();
		}
	}

	abstract protected MidiMessage getMidiMessage();

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

	public static EventProcessorMidiMessage build(MidiMessage message) {
		if (message instanceof ShortMessage)
			return EventProcessorShortMessage
					.buildShortMessage((ShortMessage) message);
		else if (message instanceof SysexMessage)
			return EventProcessorSysexMessage
					.buildSysexMessage((SysexMessage) message);
		else if (message instanceof EventProcessorSysexMessage)
			return (EventProcessorSysexMessage) message;
		else if (message instanceof EventProcessorShortMessage)
			return (EventProcessorShortMessage) message;
		throw new EventProcessorException("Cannot build message");
	}

	public static EventProcessorMidiMessage build(byte... message) {
		if (message[0] == DsiTetraMap.System_Exclusive)
			return EventProcessorSysexMessage.build(message);
		else
			return EventProcessorShortMessage.buildShortMessage(message);
	}
}
