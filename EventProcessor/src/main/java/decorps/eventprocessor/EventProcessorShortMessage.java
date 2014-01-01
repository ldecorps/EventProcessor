package decorps.eventprocessor;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.dsi.TetraParameters;

public class EventProcessorShortMessage extends EventProcessorMidiMessage {
	public static ShortMessage NullShortMessage = new ShortMessage();
	protected static DsiTetraMap dsiTetraMap = new DsiTetraMap();

	public static EventProcessorShortMessage build(int status, int second,
			int third) {
		ShortMessage shortMessage;
		try {
			shortMessage = new ShortMessage(status, second, third);
			return EventProcessorShortMessage.build(shortMessage);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	public static EventProcessorShortMessage build(MidiMessage message) {
		if (message instanceof ShortMessage)
			return new EventProcessorShortMessage((ShortMessage) message);
		if (message instanceof SysexMessage)
			return new EventProcessorShortMessageComposite(
					(SysexMessage) message);
		throw new IllegalArgumentException("Not supported type "
				+ message.getClass().getSimpleName());
	}

	public static EventProcessorShortMessage build(String status,
			String second, String third) {
		if (null != status && null != second && null != third) {
			return EventProcessorShortMessage.build(binaryToByte(status),
					binaryToByte(second), binaryToByte(third));
		} else if (null != status && null != second) {
			return EventProcessorShortMessage.build(binaryToByte(status),
					binaryToByte(second), 0);
		}
		throw new EventProcessorException(
				"cannot build shortmessage with null values yet");
	}

	public final ShortMessage shortMessage;

	protected EventProcessorShortMessage(ShortMessage message) {
		super(message.getMessage());
		this.shortMessage = message;
	}

	public EventProcessorShortMessage() {
		this(NullShortMessage);
	}

	@Override
	public Object clone() {
		return shortMessage.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return shortMessage.equals(obj);
	}

	public int getChannel() {
		return shortMessage.getChannel();
	}

	public int getCommand() {
		return shortMessage.getCommand();
	}

	public int getData1() {
		return shortMessage.getData1();
	}

	public int getData2() {
		return shortMessage.getData2();
	}

	@Override
	public int getLength() {
		return shortMessage.getLength();
	}

	@Override
	public byte[] getMessage() {
		return shortMessage.getMessage();
	}

	@Override
	public int getStatus() {
		return shortMessage.getStatus();
	}

	@Override
	public int hashCode() {
		return shortMessage.hashCode();
	}

	public boolean is(TetraParameters tetraParameter) {
		return tetraParameter.is(this);
	}

	public void send(Receiver receiver, long timestamp) {
		receiver.send(this, timestamp);
	}

	public void setMessage(int status) throws InvalidMidiDataException {
		shortMessage.setMessage(status);
	}

	public void setMessage(int status, int data1, int data2)
			throws InvalidMidiDataException {
		shortMessage.setMessage(status, data1, data2);
	}

	public void setMessage(int command, int channel, int data1, int data2)
			throws InvalidMidiDataException {
		shortMessage.setMessage(command, channel, data1, data2);
	}

	@Override
	public String toString() {
		return shortMessage.toString();
	}
}
