package decorps.eventprocessor.vendors.maps;

import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public interface EventProcessorMap {

	EventProcessorMidiMessage mapToLividSysex(
			ProgramParameterData programParameterData);

	EventProcessorMidiMessage mapLividCcToTetraNrpn(
			EventProcessorMidiMessage eventProcessorMidiMessage);

	void applyMapping();

	public abstract ProgramParameter getProgramParameter();

	public abstract List<Controller> getControllers();

	public abstract void map(ProgramParameter programParameter);

	public abstract void map(Controller controller);

	public EventProcessorMidiMessage mapToTetraNRPN(
			ProgramParameterData programParameterData);

	void refreshControllers();

	void refreshProgramParameter();

	EventProcessorMidiMessage mapToLividCcOrNote();

}
