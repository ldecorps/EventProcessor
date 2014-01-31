package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class OscillatorEncoder_to_OscillatorShapeMap extends DefaultMap {

	public OscillatorEncoder_to_OscillatorShapeMap(
			Class<? extends AbstractProgramParameter> abstractProgramParameterClass,
			int controllerNumber) {
		super(abstractProgramParameterClass, controllerNumber);
	}

	@Override
	public EventProcessorMidiMessage mapFromLividToTetra(
			EventProcessorMidiMessage messageIn) {
		EventProcessorShortMessage shortMessage = messageIn.getAsShortMessage();

		AbstractProgramParameter programParameter = buildParameter(shortMessage);
		return DsiMessageFactory.buildNRPNForProgramParameter(programParameter);

	}

	@Override
	public EventProcessorMidiMessage mapFromTetraToLivid(
			EventProcessorMidiMessage messageIn) {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
