package decorps.eventprocessor.messages;

import static decorps.eventprocessor.utils.BaseUtils.addAll;
import static decorps.eventprocessor.utils.BaseUtils.byteToBinary;
import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;
import static decorps.eventprocessor.utils.BaseUtils.representationsToBytes;

import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;

public class EventProcessorSysexMessage extends EventProcessorMidiMessage {

	private static final int bitsByPackets = 8;
	final public SysexMessage sysexMessage;

	protected static EventProcessorSysexMessage buildSysexMessage(
			SysexMessage sysexMessage) {
		EventProcessorSysexMessage result = new EventProcessorSysexMessage(
				sysexMessage.getMessage());
		return result;
	}

	protected EventProcessorSysexMessage(byte[] data) {
		super(data);
		try {
			sysexMessage = new SysexMessage();
			sysexMessage.setMessage(data, data.length);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException("Could not build sysex "
					+ bytesToHexa(data), e);
		}
	}

	@Override
	public Object clone() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public static EventProcessorSysexMessage build(byte... bytes) {
		return new EventProcessorSysexMessage(bytes);
	}

	@Override
	protected MidiMessage getMidiMessage() {
		return sysexMessage;
	}

	public static String[] unpack(String[] binary) {
		String[] result = new String[7];
		for (int i = 0; i < 7; i++) {
			int oneForSpace = i > 3 ? 1 : 0;
			char bitToMove = binary[0].charAt(8 - i - oneForSpace);
			result[i] = bitToMove + binary[i + 1].substring(1);
		}
		return result;
	}

	public static byte[] unpack(byte[] packedMessage) {
		byte[] result = new byte[0];
		int packedLength = packedMessage.length;
		int numberOfEightBytesPackets = packedLength / bitsByPackets;
		for (int currentPacketNumber = 0; currentPacketNumber < numberOfEightBytesPackets; currentPacketNumber++)
			result = unpackOnePacket(packedMessage, result, currentPacketNumber);
		int remainingBytes = packedLength - numberOfEightBytesPackets
				* bitsByPackets;
		if (0 < remainingBytes)
			throw new EventProcessorException(
					"packedMessage not made of 8 bytes packets");

		return result;
	}

	public static byte[] unpackOnePacket(byte[] packedMessage, byte[] result,
			int currentPacketNumber) {
		int from = bitsByPackets * currentPacketNumber;
		byte[] currentPacket = Arrays.copyOfRange(packedMessage, 0 + from,
				bitsByPackets + from);
		String[] binaries = new String[bitsByPackets];
		for (int i = 0; i < bitsByPackets; i++)
			binaries[i] = byteToBinary(currentPacket[i]);
		String[] unpackedPacket = unpack(binaries);
		byte[] currentPacketAsBytes = representationsToBytes(unpackedPacket);
		result = addAll(result, currentPacketAsBytes);
		return result;
	}

	public boolean isA(byte[] bytes) {
		return Arrays.equals(sysexMessage.getMessage(), bytes);
	}
}
