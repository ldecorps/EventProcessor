package decorps.eventprocessor.dsi;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static decorps.eventprocessor.utils.BaseUtils.byteToBinary;
import static decorps.eventprocessor.utils.BaseUtils.byteToHex;
import static decorps.eventprocessor.utils.BaseUtils.printOutBinaryMessage;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.EventProcessorShortMessage;
import decorps.eventprocessor.EventProcessorShortMessageComposite;

public class DsiTetraMap {

	private static final String VENDOR = "Dave Smith Instruments";
	private static final String NAME = "DSI Tetra";
	public static final byte System_Exclusive = binaryToByte("1111 0000");
	public static final byte DSI_ID = binaryToByte("0000 0001");
	public static final byte Tetra_ID = binaryToByte("0010 0110");
	public static final byte Program_Data = binaryToByte("0000 0010");
	public static final byte Request_Program_Transmit = binaryToByte("0000 0101");
	public static final byte End_Of_Exclusive = binaryToByte("1111 0111");

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

	public static boolean isTetra(Info info) {
		return NAME.equals(info.getName()) && VENDOR.equals(info.getVendor());
	}

	public boolean isProgramChange(String status, String second, String third) {
		return null == third && status.startsWith("1100");
	}

	public static byte buildBankNumber(byte bankNumber) {
		return bankNumber;
	}

	public static byte buildProgramNumber(byte programNumber) {
		return programNumber;
	}
}
