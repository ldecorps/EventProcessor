package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.maps.DefaultMap;

public class ButtonMap extends DefaultMap {

	public ButtonMap(
			Class<? extends ProgramParameter> abstractProgramParameterClass,
			int bank, int controllerNumber) {
		super(abstractProgramParameterClass, bank, controllerNumber);
	}

	@Override
	public EventProcessorMidiMessage mapFromLividToTetra(
			EventProcessorMidiMessage messageIn) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public EventProcessorMidiMessage mapFromTetraToLivid(
			EventProcessorMidiMessage messageIn) {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
