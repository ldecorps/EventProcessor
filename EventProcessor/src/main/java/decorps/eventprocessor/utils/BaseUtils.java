package decorps.eventprocessor.utils;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class BaseUtils {

	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	static public void printOutBinaryMessage(byte[] data) {
		String messageAsString = bytesToBinary(data);
		System.out.println(messageAsString);
	}

	public static String bytesToBinary(byte[] data) {
		String messageAsString = "";
		for (byte currentByte : data) {
			messageAsString += byteToBinary(currentByte) + LINE_SEPARATOR;
		}
		return messageAsString;
	}

	public static byte binaryToByte(String representation) {
		return (byte) Short.parseShort(representation.replace(" ", ""), 2);
	}

	static public String byteToBinary(byte currentByte) {
		String message = String.format("%8s",
				Integer.toBinaryString(currentByte & 0xFF)).replace(' ', '0');
		return message.substring(0, 4) + " " + message.substring(4, 8);
	}

	static public byte[] hexaToBytes(String... hexas) {
		byte[] result = new byte[hexas.length];
		int i = 0;
		for (String hexa : hexas) {
			result[i++] += (byte) Integer.parseInt(hexa, 16);
		}
		return result;
	}

	static public String hexaToBinary(String... hexas) {
		byte[] bytes = hexaToBytes(hexas);
		return bytesToBinary(bytes);
	}

	static public String binaryToHexa(String representation) {
		byte toByte = binaryToByte(representation);
		return byteToHexa(toByte);
	}

	static public String byteToHexa(byte currentByte) {
		return String.format("%02X", currentByte);
	}

	static public void printOutBytesAsHexa(byte[] data) {
		int counter = 0;
		String messageAsString = "";
		for (byte currentByte : data) {
			String byteAsBinary = byteToBinary(currentByte);
			messageAsString += byteToHexa(binaryToByte(byteAsBinary)) + " ";
			counter++;
			if (counter == 8) {
				counter = 0;
				messageAsString += LINE_SEPARATOR;
			}
		}
		System.out.println(messageAsString);
	}

	public static void printOutHexaMessage(SysexMessage message) {
		printOutBytesAsHexa(message.getData());
	}

	public static boolean MsbEquals(String expectedMsb, int actualByte) {
		final byte msb = getMsb((byte) actualByte);
		final byte expectedMsbAsByte = binaryToByte(expectedMsb);
		return msb == expectedMsbAsByte;
	}

	public static int byeToInt(byte byteToBeConverted) {
		return Integer.valueOf(
				Integer.toBinaryString(byteToBeConverted & 0xFF), 2);
	}

	public static byte getMsb(byte eightBits) {
		String binary = byteToBinary(eightBits);
		return binaryToByte(binary.substring(0, 4));
	}

	public static byte getLsb(byte eightBits) {
		String binary = byteToBinary(eightBits);
		return binaryToByte(binary.substring(4, 8));
	}

	public static String logoutMidiMessage(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		return LINE_SEPARATOR + decodeMessage(eventProcessorMidiMessage)
				+ LINE_SEPARATOR
				+ bytesToBinary(eventProcessorMidiMessage.getMessage());
	}

	public static String bytesToHexa(byte[] bytes) {
		String result = "";
		for (byte currentByte : bytes)
			result += byteToHexa(currentByte) + " ";
		return result;
	}

	public static String decodeMessage(MidiMessage message) {
		if (message instanceof ShortMessage)
			return DumpReceiver.decodeMessage((ShortMessage) message);
		else if (message instanceof SysexMessage)
			return DumpReceiver.decodeMessage((SysexMessage) message);
		else if (message instanceof MetaMessage)
			return DumpReceiver.decodeMessage((MetaMessage) message);
		else if (message instanceof EventProcessorSysexMessage)
			return DumpReceiver
					.decodeMessage(((EventProcessorSysexMessage) message).sysexMessage);
		return "Cannot decode message";
	}

	public static byte[] representationsToBytes(String[] representations) {
		byte[] result = new byte[representations.length];
		int i = 0;
		for (String representation : representations) {
			result[i++] = binaryToByte(representation);
		}
		return result;
	}

	public static String bytesToText(byte[] data) {
		return new String(data).trim();
	}

}
