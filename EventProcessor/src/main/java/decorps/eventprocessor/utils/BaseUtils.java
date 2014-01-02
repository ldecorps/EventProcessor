package decorps.eventprocessor.utils;

import javax.sound.midi.SysexMessage;

public class BaseUtils {

	static public void printOutBinaryMessage(byte[] data) {
		String messageAsString = bytesToString(data);
		System.out.println(messageAsString);
	}

	public static String bytesToString(byte[] data) {
		String messageAsString = "";
		for (byte currentByte : data) {
			messageAsString += byteToBinary(currentByte) + "\r\n";
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

	static public String byteToHex(byte currentByte) {
		return String.format("%02X", currentByte);
	}

	static public void printOutBytesAsHexa(byte[] data) {
		int counter = 0;
		String messageAsString = "";
		for (byte currentByte : data) {
			String byteAsBinary = byteToBinary(currentByte);
			messageAsString += byteToHex(binaryToByte(byteAsBinary)) + " ";
			counter++;
			if (counter == 8) {
				counter = 0;
				messageAsString += "\r\n";
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

}
