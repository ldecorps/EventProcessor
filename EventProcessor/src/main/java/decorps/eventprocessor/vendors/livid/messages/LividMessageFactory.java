package decorps.eventprocessor.vendors.livid.messages;

import static decorps.eventprocessor.utils.BaseUtils.hexasToBytes;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class LividMessageFactory {

	public static final byte[] ACK_Positive_Acknowledge = hexasToBytes("F0 00 01 61 04 7F F7");

	public static EventProcessorMidiMessage buildRequest_all_LED_indicators() {
		return new Request_all_LED_indicators();
	}

	public static EventProcessorMidiMessage buildSet_all_LED_indicators(
			int... payload) {
		return new Set_all_LED_indicators(payload, 0x04, 8);
	}

	public static EventProcessorMidiMessage buildSet_LED_Ring_indicators(
			int... payload) {
		return new Set_LED_Ring_indicators(payload, 0x1f, 64);
	}

	public static EventProcessorMidiMessage buildSet_Encoder_Values(
			int... payload) {
		return new Set_Encoder_Values(payload, 0x26, 32);
	}

	public static EventProcessorMidiMessage buildLED_Ring_Style(
			int... payloadAsCcNumbers) {
		return new Set_LED_Ring_Style(payloadAsCcNumbers, 0x32, 32);
	}

	public static EventProcessorMidiMessage buildSet_LED_Ring_Mode(
			int... payload) {
		return new Set_LED_Ring_Mode(payload);
	}

	public static EventProcessorMidiMessage buildEventProcessorNRPNMessage(
			ProgramParameter programParameter) {
		return EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(programParameter);
	}

	public static EventProcessorMidiMessage buildMap_Encosion_Mode(
			int... payload) {
		return new Set_Map_Encosion_Mode(payload, 0x11, 8);
	}

	public static EventProcessorMidiMessage build_Set_Encoder_Speed(
			int... payload) {
		return new Set_Encoder_Speed(payload, 0x1e, 2);
	}

	public static EventProcessorMidiMessage build_Switch_Relative_Local_Control_Off() {
		return new LocalControl(122);
	}

	public static EventProcessorMidiMessage build_Button_Toggle_Mode_Enable(
			int... payload) {
		return new Button_Toggle_Mode_Enable(payload, (byte) 0x36, 12);
	}

	public static EventProcessorMidiMessage build_Request_Button_mapping() {
		return new Request_Button_mapping(new int[] { 0x0b }, (byte) 0x7, 1);
	}

	public static EventProcessorMidiMessage build_Map_Buttons(int... payload) {
		return new Map_Buttons(payload, (byte) 0x0B, 45);
	}
}
