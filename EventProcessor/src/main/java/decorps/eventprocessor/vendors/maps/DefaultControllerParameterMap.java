package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class DefaultControllerParameterMap implements EventProcessorMap {
	@Override
	public String toString() {
		return "DefaultControllerParameterMap [controllers=" + controllers
				+ ", programParameter=" + programParameter + "]";
	}

	final List<Controller> controllers = new ArrayList<Controller>();
	final ProgramParameter programParameter;

	public List<Controller> getControllers() {
		return controllers;
	}

	public ProgramParameter getProgramParameter() {
		return programParameter;
	}

	public DefaultControllerParameterMap(ProgramParameter programParameter,
			Controller... controllers) {
		for (Controller controller : controllers) {
			this.controllers.add(controller);
		}
		this.programParameter = programParameter;
		MapRepository.register(this);
	}

	public EventProcessorMidiMessage mapToTetraNRPN(
			ProgramParameterData programParameterData) {
		EventProcessorMidiMessage messageForLivid = LividMessageFactory
				.buildEventProcessorNRPNMessage(programParameter);
		return messageForLivid;
	}

	public EventProcessorMidiMessage mapToLividSysex(
			ProgramParameterData programParameterData) {
		return new SetEncodersAndLedIndicatorsRule()
				.buildMidiMessagesForProgramParameterData(programParameterData);
	}

	public EventProcessorMidiMessage map(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void applyMapping() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void map(Controller controller) {
		programParameter.setValue(controller.getRebasedValue());
	}

	public void map(ProgramParameter programParameter) {
		final Controller controller = controllers.get(0);
		controller.setProgramParameter(programParameter);
	}

	public void refreshControllers() {
		final byte newValue = programParameter.getRebasedValue();
		controllers.get(0).setValue(newValue);
	}

	public void refreshProgramParameter() {
		programParameter.setValue(controllers.get(0).getValue());
	}

	public boolean contains(Controller controller) {
		return controllers.contains(controller);
	}

	public EventProcessorMidiMessage mapToLividCc() {
		return EventProcessorShortMessage.buildShortMessage(
				ShortMessage.CONTROL_CHANGE,
				BankLayout.CurrentBank.bankNumber - 1, getControllers().get(0)
						.getCCNumber(), programParameter.getRebasedValue());

	}
}
