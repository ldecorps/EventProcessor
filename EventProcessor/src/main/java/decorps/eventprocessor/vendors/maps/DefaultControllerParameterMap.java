package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public class DefaultControllerParameterMap implements EventProcessorMap {
	final List<Controller> controllers = new ArrayList<Controller>();
	final AbstractProgramParameter abstractProgramParameter;

	@Override
	public List<Controller> getControllers() {
		return controllers;
	}

	@Override
	public AbstractProgramParameter getAbstractProgramParameter() {
		return abstractProgramParameter;
	}

	public DefaultControllerParameterMap(
			AbstractProgramParameter abstractProgramParameter,
			Controller... controllers) {
		for (Controller controller : controllers) {
			this.controllers.add(controller);
		}
		this.abstractProgramParameter = abstractProgramParameter;
		MapRepository.register(this);
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

	@Override
	public void map(Controller controller) {
		abstractProgramParameter.setValue(controller.getRebasedValue());
	}

	@Override
	public void map(AbstractProgramParameter abstractProgramParameter) {
		controllers.get(0).setValue(abstractProgramParameter.getValue());
	}

	@Override
	public void refreshControllers() {
		final byte newValue = abstractProgramParameter.getRebasedValue();
		controllers.get(0).setValue(newValue);
	}

	@Override
	public void refreshProgramParameter() {
		abstractProgramParameter.setValue(controllers.get(0).getValue());
	}

}
