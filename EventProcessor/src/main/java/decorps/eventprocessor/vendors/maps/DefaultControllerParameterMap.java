package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Button;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.Encoder;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class DefaultControllerParameterMap implements EventProcessorMap {
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [controllers=" + controllers
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((controllers == null) ? 0 : controllers.hashCode());
		result = prime
				* result
				+ ((programParameter == null) ? 0 : programParameter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultControllerParameterMap other = (DefaultControllerParameterMap) obj;
		if (controllers == null) {
			if (other.controllers != null)
				return false;
		} else if (!controllers.equals(other.controllers))
			return false;
		if (programParameter == null) {
			if (other.programParameter != null)
				return false;
		} else if (!programParameter.equals(other.programParameter))
			return false;
		return true;
	}

	public DefaultControllerParameterMap(ProgramParameter programParameter,
			Controller... controllers) {
		synchronized (DefaultControllerParameterMap.class) {
			for (Controller controller : controllers) {
				controller.setProgramParameter(programParameter);
				this.controllers.add(controller);
			}
			this.programParameter = programParameter;
			MapRepository.register(this);
		}
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

	public EventProcessorMidiMessage mapLividCcToTetraNrpn(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		int newCcValue = eventProcessorMidiMessage.getAsShortMessage()
				.getData2();
		programParameter.setValue(getController(), (byte) newCcValue);
		return EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(programParameter);
	}

	private Controller getController() {
		return controllers.get(0);
	}

	public void applyMapping() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void map(Controller controller) {
		programParameter.setValue(controller, controller.getRebasedValue());
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
		final Controller controller = controllers.get(0);
		programParameter.setValue(controller, controller.getValue());
	}

	public boolean contains(Controller controller) {
		return controllers.contains(controller);
	}

	public EventProcessorMidiMessage mapToLividCcOrNote() {
		final byte rebasedValue = programParameter.getRebasedValue();
		final Controller controller = getControllers().get(0);
		final int ccOrNoteNumber = controller.getCCOrNoteNumber();
		final int channel = BankLayout.CurrentBank.bankNumber - 1;
		final int type = controller instanceof Button ? ShortMessage.NOTE_ON
				: ShortMessage.CONTROL_CHANGE;
		return EventProcessorShortMessage.buildShortMessage(type, channel,
				ccOrNoteNumber, rebasedValue);

	}

	public static void mapToEncoder(
			Class<? extends ProgramParameter> programParameterClass,
			int encoderId) {
		ProgramParameter programParameter = BankLayout
				.getCurrentProgramParameter(programParameterClass);
		new DefaultControllerParameterMap(programParameter,
				new Encoder[] { BankLayout.CurrentBank.encoders[encoderId] });
	}
}
