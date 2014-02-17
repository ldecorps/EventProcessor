package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Encoder;

public class RelativeEncoderChangeEchoesNewLEDRingValue implements Rule {

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage ccComingFromLivid) {
		EventProcessorShortMessage shortMessage = ccComingFromLivid
				.getAsShortMessage();

		int newValue = getRebasedValue(shortMessage);

		final EventProcessorMidiMessage buildShortMessage = EventProcessorShortMessage.buildShortMessage(
				shortMessage.getCommand(), shortMessage.getChannel(),
				shortMessage.getData1(), newValue);
		return buildShortMessage;

	}

	private int getRebasedValue(EventProcessorShortMessage shortMessage) {
		final int data1 = shortMessage.getData1();
		Encoder encoder = BankLayout.CurrentBank.getEncoderForCc(data1);
		ProgramParameter programParameter = encoder.getProgramParameter();
		return programParameter.getRebasedValue();
	}

}
