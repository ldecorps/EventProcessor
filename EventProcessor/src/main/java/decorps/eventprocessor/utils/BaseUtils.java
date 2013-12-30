package decorps.eventprocessor.utils;

import javax.sound.midi.SysexMessage;

public class BaseUtils {

	static public void printOutBinaryMessage(byte[] data) {
		String messageAsString = "";
		for (byte currentByte : data) {
			messageAsString += byteToBinary(currentByte) + "\r\n";
		}
		System.out.println(messageAsString);
	}

	public static byte binaryToByte(String representation) {
		return (byte) Short.parseShort(
				representation.substring(0, 9).replace(" ", ""), 2);
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

}
