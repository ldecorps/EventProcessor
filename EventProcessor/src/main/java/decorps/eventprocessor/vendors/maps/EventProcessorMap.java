package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public interface EventProcessorMap {

	EventProcessorMidiMessage map(
			AbstractProgramParameter abstractProgramParameter);

	EventProcessorMidiMessage mapToCcs(ProgramParameterData programparameterdata);

	EventProcessorMidiMessage map(ProgramParameterData programparameterdata);

	EventProcessorMidiMessage map(
			EventProcessorMidiMessage eventProcessorMidiMessage);

}
