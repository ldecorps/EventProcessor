package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public interface EventProcessorMap {

	LividCodeEventProcessorCCShortMessage map(
			AbstractProgramParameter abstractProgramParameter);

}
