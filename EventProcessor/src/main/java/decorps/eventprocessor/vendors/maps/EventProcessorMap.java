package decorps.eventprocessor.vendors.maps;

import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public interface EventProcessorMap {

	EventProcessorMidiMessage mapLividCcToTetraNrpn(
			EventProcessorMidiMessage eventProcessorMidiMessage);

	public abstract ProgramParameter getProgramParameter();

	public abstract List<Controller> getControllers();

	public abstract void map(ProgramParameter programParameter);

	void refreshControllers();

}
