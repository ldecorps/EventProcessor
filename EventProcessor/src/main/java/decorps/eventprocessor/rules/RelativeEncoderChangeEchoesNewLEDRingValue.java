package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Encoder;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class RelativeEncoderChangeEchoesNewLEDRingValue implements Rule {

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage ccComingFromLivid) {
		EventProcessorShortMessage shortMessage = ccComingFromLivid
				.getAsShortMessage();

		int newValue = getRebasedValue(shortMessage);

		return EventProcessorShortMessage.buildShortMessage(
				shortMessage.getCommand(), shortMessage.getChannel(),
				shortMessage.getData1(), newValue);

	}

	private int getRebasedValue(EventProcessorShortMessage shortMessage) {
		Encoder encoder = BankLayout.CurrentBank.getEncoderForCc(shortMessage
				.getData1());
		ProgramParameter programParameter = MapRepository
				.getParameterForController(encoder);
		return programParameter.getRebasedValue();
	}

}
