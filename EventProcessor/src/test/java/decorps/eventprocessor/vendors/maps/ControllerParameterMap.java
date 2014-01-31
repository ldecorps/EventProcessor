package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public class ControllerParameterMap implements EventProcessorMap {
	final List<Controller> controllers = new ArrayList<Controller>();

	@Override
	public List<Controller> getControllers() {
		return controllers;
	}

	final Class<? extends AbstractProgramParameter> abstractProgramParameterClass;

	@Override
	public Class<? extends AbstractProgramParameter> getAbstractProgramParameterClass() {
		return abstractProgramParameterClass;
	}

	public ControllerParameterMap(
			Class<? extends AbstractProgramParameter> abstractProgramParameterClass,
			Controller... controllers) {
		for (Controller controller : controllers) {
			this.controllers.add(controller);
		}
		this.abstractProgramParameterClass = abstractProgramParameterClass;
		MapRepository.register(this);
	}

	@Override
	public EventProcessorMidiMessage map(
			AbstractProgramParameter abstractProgramParameter) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public EventProcessorMidiMessage mapToCcs(
			ProgramParameterData programparameterdata) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public EventProcessorMidiMessage map(
			ProgramParameterData programparameterdata) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public EventProcessorMidiMessage map(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public void applyMapping() {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
