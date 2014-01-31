package decorps.eventprocessor.vendors.maps;

import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public interface EventProcessorMap {

	EventProcessorMidiMessage map(
			AbstractProgramParameter abstractProgramParameter);

	EventProcessorMidiMessage mapToCcs(ProgramParameterData programparameterdata);

	EventProcessorMidiMessage map(ProgramParameterData programparameterdata);

	EventProcessorMidiMessage map(
			EventProcessorMidiMessage eventProcessorMidiMessage);

	void applyMapping();

	public abstract Class<? extends AbstractProgramParameter> getAbstractProgramParameterClass();

	public abstract List<Controller> getControllers();

}
