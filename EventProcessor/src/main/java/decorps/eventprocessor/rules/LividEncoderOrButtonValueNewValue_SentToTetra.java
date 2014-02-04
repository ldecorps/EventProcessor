package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class LividEncoderOrButtonValueNewValue_SentToTetra implements Rule {

	public LividEncoderOrButtonValueNewValue_SentToTetra() {
	}

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		ProgramParameter programParameter = MapRepository
				.getParameterForController(ControllerRepository
						.getControllerForLividShortMessage(eventProcessorMidiMessage));
		programParameter.setValue((byte) eventProcessorMidiMessage
				.getAsShortMessage().getData2());
		EventProcessorMidiMessage nrpn = DsiMessageFactory
				.buildNRPNForProgramParameter(programParameter);
		return nrpn;
	}

}
