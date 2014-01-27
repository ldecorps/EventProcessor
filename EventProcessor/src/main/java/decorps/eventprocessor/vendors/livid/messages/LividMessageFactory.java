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

	public static EventProcessorMidiMessage buildSet_encoder_values(
			int... payload) {
		return new Set_encoder_values(payload, 0x26, 32);
	}

}
