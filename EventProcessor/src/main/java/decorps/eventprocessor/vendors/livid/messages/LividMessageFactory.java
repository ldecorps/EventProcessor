package decorps.eventprocessor.vendors.livid.messages;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class LividMessageFactory {

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

	public static EventProcessorMidiMessage buildLED_Ring_Style(int... payload) {
		return new Set_LED_Ring_Style(payload, 0x32, 32);
	}

	public static EventProcessorMidiMessage buildSet_LED_Ring_Mode(int... payload) {
		return new Set_LED_Ring_Mode(payload);
	}

}
