package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.livid.Encoder;

public class RelativeEncoderChangeEchoesNewLEDRingValue implements Rule {

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage ccComingFromLivid) {
		EventProcessorShortMessage shortMessage = ccComingFromLivid
				.getAsShortMessage();

		int newValue = getRebasedValue(shortMessage);

		int ledRingCc = ControllerRepository
				.getLedRingCcForLividCc(shortMessage.getData1());

		final EventProcessorMidiMessage buildShortMessage = EventProcessorShortMessage
				.buildShortMessage(shortMessage.getCommand(),
						shortMessage.getChannel(), ledRingCc, newValue);

		return buildShortMessage;
	}

	private int getRebasedValue(EventProcessorShortMessage shortMessage) {
		final int data1 = shortMessage.getData1();
		Encoder encoder = BankLayout.CurrentBank.getEncoderForCc(data1);
		ProgramParameter programParameter = encoder.getProgramParameter();
		final byte rebasedValue = programParameter.getRebasedValue();
		return rebasedValue;
	}

}
