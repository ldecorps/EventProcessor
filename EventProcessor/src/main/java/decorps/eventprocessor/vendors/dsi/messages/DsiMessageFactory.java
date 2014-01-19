package decorps.eventprocessor.vendors.dsi.messages;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class DsiMessageFactory {
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

	public static EventProcessorMidiMessage buildUniversal_System_Exclusive_Message_Device_Inquiry() {
		return new Universal_System_Exclusive_Message_Device_Inquiry();
	}

	public static EventProcessorMidiMessage buildMode_Change__ProgramChange() {
		return new Mode_Change__ProgramChange();
	}

	public static EventProcessorMidiMessage buildMode_Change__ComboChange() {
		return new Mode_Change__ComboChange();
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

	public static EventProcessorMidiMessage buildProgramEditBufferDumpRequest() {
		return new ProgramEditBufferDumpRequest();
	}
}
