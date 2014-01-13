package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static decorps.eventprocessor.utils.BaseUtils.byteToBinary;
import static decorps.eventprocessor.utils.BaseUtils.byteToHexa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessageComposite;

public class DsiTetraMap {

	private static final String VENDOR = "Dave Smith Instruments";
	private static final String NAME = "DSI Tetra";
	private static final String MMJ_NAME = "DSI Tetra - DSI Tetra";

	public static final byte System_Exclusive = binaryToByte("1111 0000");
	public static final byte Non_realtime_message = binaryToByte("0111 1110");
	public static final byte Midi_Channel_all = binaryToByte("0111 1111");
	public static final byte Inquiry_Message = binaryToByte("0000 0110");
	public static final byte Inquiry_Request = binaryToByte("0000 0001");
	public static final byte DSI_ID = binaryToByte("0000 0001");
	public static final byte Tetra_ID = binaryToByte("0010 0110");
	public static final byte Program_Data = binaryToByte("0000 0010");
	public static final byte Edit_Buffer_Data = binaryToByte("0000 0011");
	public static final byte Request_Program_Transmit = binaryToByte("0000 0101");
	public static final byte RequestProgramEditBufferTransmit = binaryToByte("0000 0110");

	public static final byte Select_Program_Mode = binaryToByte("0011 0000");
	public static final byte Select_Combo_Mode = binaryToByte("0011 0001");

	public static final byte End_Of_Exclusive = binaryToByte("1111 0111");

	public static final byte[] Universal_System_Exclusive_Message_Device_Inquiry = new byte[] {
			System_Exclusive, Non_realtime_message, Midi_Channel_all,
			Inquiry_Message, Inquiry_Request, End_Of_Exclusive };
	public static final byte[] Mode_Change__ProgramChange = new byte[] {
			System_Exclusive, DSI_ID, Tetra_ID, Select_Program_Mode,
			End_Of_Exclusive };
	public static final byte[] Mode_Change__ComboChange = new byte[] {
			System_Exclusive, DSI_ID, Tetra_ID, Select_Combo_Mode,
			End_Of_Exclusive };

	private byte[] messageAsBytes;

	public EventProcessorShortMessageComposite convert(SysexMessage message) {
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
		return isProgramDataDump(messageAsBytes);
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
							+ byteToHexa(currentByte) + " "
							+ byteToBinary(currentByte), e);
		}
		EventProcessorShortMessage eventProcessorShortMessage = (EventProcessorShortMessage) EventProcessorMidiMessage
				.build(shortMessage);
		eventProcessorShortMessageComposite.add(eventProcessorShortMessage);
	}

	public boolean isEndOfExclusive(byte currentByte) {
		return -9 == currentByte;
	}

	public static boolean isTetra(Info info) {
		return (NAME.equals(info.getName()) && VENDOR.equals(info.getVendor()))
				|| info.getName().equals("USB Uno MIDI Interface")
				|| info.getName().equals(MMJ_NAME);
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

	public boolean isSystemExclusive(byte b) {
		return System_Exclusive == b;
	}

	public static boolean isProgramDataDump(byte[] sysexMessage) {
		if (445 != sysexMessage.length)
			return false;
		boolean result = true;
		int index = 0;
		result &= sysexMessage[index++] == System_Exclusive;
		result &= sysexMessage[index++] == DSI_ID;
		result &= sysexMessage[index++] == Tetra_ID;
		result &= sysexMessage[index++] == Program_Data;
		result &= sysexMessage[sysexMessage.length - 1] == End_Of_Exclusive;
		return result;
	}

	public static boolean isProgramEditBufferDataDump(byte[] sysexMessage) {
		if (sysexMessage.length != 444)
			return false;
		boolean result = true;
		int index = 0;
		result &= sysexMessage[index++] == System_Exclusive;
		result &= sysexMessage[index++] == DSI_ID;
		result &= sysexMessage[index++] == Tetra_ID;
		result &= sysexMessage[index++] == Edit_Buffer_Data;
		result &= sysexMessage[sysexMessage.length - 1] == End_Of_Exclusive;
		return result;
	}
}
