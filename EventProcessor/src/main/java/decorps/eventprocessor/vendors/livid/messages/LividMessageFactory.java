package decorps.eventprocessor.vendors.livid.messages;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class LividMessageFactory {

	public static EventProcessorMidiMessage buildRequest_all_LED_indicators() {
		return new Request_all_LED_indicators();
	}

}
