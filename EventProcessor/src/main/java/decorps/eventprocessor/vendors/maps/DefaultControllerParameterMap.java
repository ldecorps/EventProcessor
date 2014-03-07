package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.Encoder;

public class DefaultControllerParameterMap implements EventProcessorMap {
	final List<Controller> controllers = new ArrayList<Controller>();
	final ProgramParameter programParameter;

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [controllers=" + controllers
				+ ", programParameter=" + programParameter + "]";
	}

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
		for (Controller controller : controllers) {
			controller.setProgramParameter(programParameter);
			this.controllers.add(controller);
		}
		this.programParameter = programParameter;
		MapRepository.register(this);
	}

	public EventProcessorMidiMessage mapLividCcToTetraNrpn(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		int newCcValue = eventProcessorMidiMessage.getAsShortMessage()
				.getData2();
		final Controller controller = getController();
		programParameter.setValue(controller, (byte) newCcValue);
		return EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(programParameter);
	}

	private Controller getController() {
		return controllers.get(0);
	}

	public void map(ProgramParameter programParameter) {
		final Controller controller = controllers.get(0);
		controller.setProgramParameter(programParameter);
	}

	public boolean contains(Controller controller) {
		return controllers.contains(controller);
	}

	public static void mapToEncoder(
			Class<? extends ProgramParameter> programParameterClass,
			int encoderId) {
		ProgramParameter programParameter = BankLayout
				.getCurrentProgramParameterALayer(programParameterClass);
		new DefaultControllerParameterMap(programParameter,
				new Encoder[] { BankLayout.CurrentBank.encoders[encoderId] });
	}

}
