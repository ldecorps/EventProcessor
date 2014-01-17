package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public interface EventProcessorMap {

	LividCodeEventProcessorCCShortMessage map(
			AbstractProgramParameter abstractProgramParameter);

	LividCodeEventProcessorCCShortMessageComposite mapToCcs(
			ProgramParameterData programparameterdata);

	EventProcessorMidiMessageComposite map(
			ProgramParameterData programparameterdata);

}
