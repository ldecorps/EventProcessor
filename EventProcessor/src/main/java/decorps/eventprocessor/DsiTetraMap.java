package decorps.eventprocessor;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

public class DsiTetraMap {

	private static final String VENDOR = "Dave Smith Instruments";
	private static final String NAME = "DSI Tetra";
	public static final byte DSI_ID = binaryToByte("0000 0001");
	public static final byte Tetra_ID = binaryToByte("0010 0110");
	public static final byte Program_Data = binaryToByte("0000 0010");
	private byte[] messageAsBytes;

	public EventProcessorShortMessageComposite convert(SysexMessage message) {
		printOutBinaryMessage(message.getData());
		EventProcessorShortMessageComposite eventProcessorShortMessageComposite = EventProcessorShortMessageComposite
				.build();
		messageAsBytes = message.getData();
		if (!isValidTetraProgramDump())
			return eventProcessorShortMessageComposite;
		for (int i = 5; i < messageAsBytes.length; i++) {
			doByte(eventProcessorShortMessageComposite, messageAsBytes[i]);
		}
		return eventProcessorShortMessageComposite;
	}

	boolean isValidTetraProgramDump() {
		return 444 == messageAsBytes.length && messageAsBytes[0] == DSI_ID
				&& messageAsBytes[1] == Tetra_ID
				&& messageAsBytes[2] == Program_Data;
	}

	public static void printOutHexaMessage(SysexMessage message) {
		printOutBytesAsHexa(message.getData());
	}

	static void printOutBytesAsHexa(byte[] data) {
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

	static void printOutBinaryMessage(byte[] data) {
		String messageAsString = "";
		for (byte currentByte : data) {
			messageAsString += byteToBinary(currentByte) + "\r\n";
		}
		System.out.println(messageAsString);
	}

	static public String byteToHex(byte currentByte) {
		return String.format("%02X", currentByte);
	}

	private void doByte(
			EventProcessorShortMessageComposite eventProcessorShortMessageComposite,
			byte currentByte) {
		if (isEndOfExclusive(currentByte))
			return;
		ShortMessage shortMessage = new ShortMessage();
		try {
			shortMessage.setMessage(ShortMessage.CONTROL_CHANGE, 0,
					currentByte, 1);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(
					"Could not sent control change for byte: "
							+ byteToHex(currentByte) + " "
							+ byteToBinary(currentByte), e);
		}
		EventProcessorShortMessage eventProcessorShortMessage = EventProcessorShortMessage
				.build(shortMessage);
		eventProcessorShortMessageComposite.add(eventProcessorShortMessage);
	}

	public boolean isEndOfExclusive(byte currentByte) {
		return -9 == currentByte;
	}

	static public String byteToBinary(byte currentByte) {
		String message = String.format("%8s",
				Integer.toBinaryString(currentByte & 0xFF)).replace(' ', '0');
		return message.substring(0, 4) + " " + message.substring(4, 8);
	}

	public static byte binaryToByte(String representation) {
		return (byte) Short.parseShort(
				representation.substring(0, 9).replace(" ", ""), 2);
	}

	public static boolean isTetra(Info info) {
		return NAME.equals(info.getName()) && VENDOR.equals(info.getVendor());
	}

}
