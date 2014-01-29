package decorps.eventprocessor.messages;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;

public class EventProcessorShortMessage extends EventProcessorMidiMessage {
	public static ShortMessage NullShortMessage = new ShortMessage();

	public static EventProcessorMidiMessage buildShortMessage(int status,
			int second, int third) {
		ShortMessage shortMessage;
		try {
			shortMessage = new ShortMessage();
			shortMessage.setMessage(status, second, third);
			return EventProcessorShortMessage.buildShortMessage(shortMessage);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	public static EventProcessorMidiMessage buildShortMessage(int command,
			int channel, int data1, int data2) {
		ShortMessage shortMessage;
		try {
			shortMessage = new ShortMessage();
			shortMessage.setMessage(command, channel, data1, data2);
			return EventProcessorShortMessage.buildShortMessage(shortMessage);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	protected static EventProcessorShortMessage buildShortMessage(
			ShortMessage message) {
		return new EventProcessorShortMessage(message);
	}

	public static EventProcessorMidiMessage build(String status, String second,
			String third) {
		if (null != status && null != second && null != third) {
			return EventProcessorShortMessage.buildShortMessage(
					binaryToByte(status), binaryToByte(second),
					binaryToByte(third));
		} else if (null != status && null != second) {
			return EventProcessorShortMessage.buildShortMessage(
					binaryToByte(status), binaryToByte(second), 0);
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

	@Override
	protected MidiMessage getMidiMessage() {
		return shortMessage;
	}

	public static EventProcessorMidiMessage buildShortMessage(byte[] message) {
		if (3 != message.length)
			throw new EventProcessorException("ShortMessage should be 3 bytes");
		return buildShortMessage(message[0], message[1], message[2]);
	}
}
