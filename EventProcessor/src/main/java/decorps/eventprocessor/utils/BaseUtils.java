package decorps.eventprocessor.utils;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.exceptions.WrongSysexPayloadSize;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
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
		final byte msb = getFirstFourBits((byte) actualByte);
		final byte expectedMsbAsByte = binaryToByte(expectedMsb);
		return msb == expectedMsbAsByte;
	}

	public static int byeToInt(byte byteToBeConverted) {
		return Integer.valueOf(
				Integer.toBinaryString(byteToBeConverted & 0xFF), 2);
	}

	public static byte getFirstFourBits(byte eightBits) {
		String binary = byteToBinary(eightBits);
		return binaryToByte(binary.substring(0, 4));
	}

	public static byte getLastFourBits(byte eightBits) {
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
		if (ShortMessage.class.isAssignableFrom(message.getClass()))
			return DumpReceiver.decodeMessage((ShortMessage) message);
		else if (EventProcessorShortMessage.class.isAssignableFrom(message
				.getClass()))
			return DumpReceiver
					.decodeMessage(((EventProcessorShortMessage) message).shortMessage);
		else if (message instanceof SysexMessage)
			return DumpReceiver.decodeMessage((SysexMessage) message);
		else if (message instanceof MetaMessage)
			return DumpReceiver.decodeMessage((MetaMessage) message);
		else if (message instanceof EventProcessorSysexMessage)
			return DumpReceiver
					.decodeMessage(((EventProcessorSysexMessage) message).sysexMessage);
		else if (message instanceof EventProcessorMidiMessageComposite) {
			String result = " Composite: ";
			for (EventProcessorMidiMessage currentMessage : ((EventProcessorMidiMessageComposite) message).eventProcessorMidiMessages)
				result += LINE_SEPARATOR
						+ BaseUtils.decodeMessage(currentMessage);
			return result;
		}
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

	public static byte[] putInArrayAfter(byte[] array, int addAfter,
			int... bytes) {
		for (int i = 0; i < bytes.length; i++)
			array[i + addAfter + 1] = (byte) bytes[i];
		return array;
	}

	public static void checkSizeIs(int expectedLength, int[] bytes) {
		if (bytes.length != expectedLength) {
			WrongSysexPayloadSize wrongSysexPayloadSize = new WrongSysexPayloadSize(
					"expected " + expectedLength + " was " + bytes.length);
			wrongSysexPayloadSize.printStackTrace();
			throw wrongSysexPayloadSize;
		}
	}

	public static void checkSizeIsNotMoreThan(int maximumLength, int[] bytes) {
		if (bytes.length > maximumLength) {
			WrongSysexPayloadSize wrongSysexPayloadSize = new WrongSysexPayloadSize(
					"expected not more than " + maximumLength + " was "
							+ bytes.length);
			wrongSysexPayloadSize.printStackTrace();
			throw wrongSysexPayloadSize;
		}
	}

	public static String intToHexa(int i) {
		return Integer.toHexString(i);
	}

	public static int[] bytesToInts(byte[] bytes) {
		int[] result = new int[bytes.length];
		for (int i = 0; i < result.length; i++)
			result[i] = bytes[i];
		return result;
	}

	public static byte[] addAll(byte[] array1, byte[] array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	public static byte[] clone(byte[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	public static String reverseString(String stringToReverse) {
		String result = "";
		for (int i = stringToReverse.length(); i > 0; i--)
			result += stringToReverse.substring(i - 1, i - 0);
		return result;

	}
}
