package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.Layer;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Button;
import decorps.eventprocessor.vendors.livid.Controller;

public class Oscillator1ShapeMap extends DefaultControllerParameterMap {

	public Oscillator1ShapeMap(ProgramParameter programParameter,
			Controller[] controllers) {
		super(programParameter, controllers);
	}

	public Oscillator1ShapeMap() {
		this(registerParameter(), new Controller[] { getButton() });
	}

	private static Controller getButton() {
		final Button button = BankLayout.Bank1.buttons[0];
		return button;
	}

	private static ProgramParameter registerParameter() {
		final Layer currentLayer = BankLayout.getCurrentProgramParameterData()
				.currentLayer();
		return currentLayer.oscillator1Shape;
	}

	@Override
	public EventProcessorMidiMessage mapLividCcToTetraNrpn(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		int newCcValue = eventProcessorMidiMessage.getAsShortMessage()
				.getData2();
		if (0 == newCcValue)
			return switchOscillatorOff();
		throw new EventProcessorException("Not implemented yet");
	}

	private EventProcessorMidiMessage switchOscillatorOff() {
		final ProgramParameter oscillator1Shape = BankLayout
				.getCurrentProgramParameterData().A.oscillator1Shape;
		oscillator1Shape.setValue(getButton(), (byte) 0);
		EventProcessorMidiMessage result = EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(oscillator1Shape);
		return result;
	}
}
