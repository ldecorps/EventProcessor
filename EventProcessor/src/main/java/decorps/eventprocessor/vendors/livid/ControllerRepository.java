package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class ControllerRepository {

	public static Controller getControllerForLividShortMessage(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		return BankLayout.CurrentBank.encoders[eventProcessorMidiMessage
				.getAsShortMessage().getData1() + 1];
	}

}
