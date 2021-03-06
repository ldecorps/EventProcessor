package decorps.eventprocessor.messages;

import static decorps.eventprocessor.utils.BaseUtils.decodeMessage;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.System_Exclusive;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;
import decorps.eventprocessor.vendors.dsi.ProgramDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramEditBufferDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.MessageType;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;

public abstract class EventProcessorMidiMessage extends MidiMessage {

	public static final EventProcessorMidiMessage nullEventProcessorMidiMessage = new EventProcessorMidiMessage(
			new byte[] {}) {

		@Override
		protected MidiMessage getMidiMessage() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		@Override
		public Object clone() {
			throw new EventProcessorException("Not Implemented Yet");
		}
	};
	protected static DsiTetraMap dsiTetraMap = new DsiTetraMap();
	public static Object wait = new Object();

	protected EventProcessorMidiMessage(byte[] data) {
		super(data);
	}

	public boolean is(MessageType messageType) {
		return messageType.is(this);
	}

	public EventProcessorShortMessage getAsShortMessage() {
		if (this instanceof EventProcessorShortMessage)
			return (EventProcessorShortMessage) this;
		RuntimeException e = new ClassCastException(
				"not a EventProcessorShortMessage: "
						+ this.getClass().getSimpleName());
		e.printStackTrace();
		throw e;
	}

	public synchronized void send(Receiver receiver, long timestamp) {
		final MidiMessage midiMessage = getMidiMessage();
		receiver.send(midiMessage, timestamp);
	}

	abstract protected MidiMessage getMidiMessage();

	public EventProcessorSysexMessage getAsSysexMessage() {
		if (this instanceof EventProcessorSysexMessage)
			return (EventProcessorSysexMessage) this;
		throw new ClassCastException("not a EventProcessorSysexMessage");
	}

	public EventProcessorNRPNMessage getAsEventProcessorNRPNMessage() {
		if (this instanceof EventProcessorNRPNMessage)
			return (EventProcessorNRPNMessage) this;
		RuntimeException e = new ClassCastException(
				"not a EventProcessorNRPNMessage: "
						+ this.getClass().getSimpleName());
		e.printStackTrace();
		throw e;
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
		if (message[0] == System_Exclusive)
			return EventProcessorSysexMessage.build(message);
		else
			return EventProcessorShortMessage.buildShortMessage(message);
	}

	public EventProcessorMidiMessageComposite getAsComposite() {
		return (EventProcessorMidiMessageComposite) this;
	}

	public boolean isNote() {
		if (!isShortMessage())
			return false;
		if (getAsShortMessage().isNoteOn() || getAsShortMessage().isNoteOff())
			return true;
		return false;
	}

	public ProgramParameterData getAsProgramParameterData() {
		if (DsiTetraMap.isProgramEditBufferDataDump(getMessage()))
			return ProgramEditBufferDataDump
					.buildProgramEditBufferDataDump(getMessage()).programParameterData;
		if (DsiTetraMap.isProgramDataDump(getMessage()))
			return ProgramDataDump.buildProgramDataDump(getMessage()).programParameterData;
		throw new EventProcessorException("not supported");
	}

	public boolean isCC() {
		if (!isShortMessage())
			return false;
		return getAsShortMessage().getCommand() == ShortMessage.CONTROL_CHANGE;
	}

	@Override
	public String toString() {
		return decodeMessage(this);
	}

	public EventProcessorMidiMessage getMessageFromComposite(int i) {
		return getAsComposite().getMessages().get(i);
	}

	public boolean isNRPN() {
		return this instanceof EventProcessorNRPNMessage;
	}

	public boolean isComposite() {
		return this instanceof EventProcessorMidiMessageComposite;
	}

}
