package decorps.eventprocessor.vendors.maps;

import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public interface EventProcessorMap {

	EventProcessorMidiMessage mapToCcs(ProgramParameterData programparameterdata);

	EventProcessorMidiMessage map(ProgramParameterData programparameterdata);

	EventProcessorMidiMessage map(
			EventProcessorMidiMessage eventProcessorMidiMessage);

	void applyMapping();

	public abstract AbstractProgramParameter getAbstractProgramParameter();

	public abstract List<Controller> getControllers();

	public abstract void map(AbstractProgramParameter abstractProgramParameter);

	public abstract void map(Controller controller);

	void refreshControllers();

	void refreshProgramParameter();

}
