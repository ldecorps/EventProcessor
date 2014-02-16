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
		this(registerParameter(), new Controller[] { getOnOffButton(),
				getSawtoothButton() });
	}

	static Button getOnOffButton() {
		final Button button = BankLayout.Bank1.buttons[0];
		return button;
	}

	static Button getSawtoothButton() {
		final Button button = BankLayout.Bank1.buttons[8];
		return button;
	}

	static Button getTriangleButton() {
		final Button button = BankLayout.Bank1.buttons[16];
		return button;
	}

	static Button getSquareButton() {
		final Button button = BankLayout.Bank1.buttons[24];
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
		else if (1 == newCcValue)
			return switchSawtoothOn();
		throw new EventProcessorException("Not implemented yet");
	}

	private EventProcessorMidiMessage switchSawtoothOn() {
		final ProgramParameter oscillator1Shape = BankLayout
				.getCurrentProgramParameterData().A.oscillator1Shape;
		oscillator1Shape.setValue(getSawtoothButton(), (byte) 1);
		EventProcessorMidiMessage result = EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(oscillator1Shape);
		return result;
	}

	private EventProcessorMidiMessage switchOscillatorOff() {
		final ProgramParameter oscillator1Shape = BankLayout
				.getCurrentProgramParameterData().A.oscillator1Shape;
		oscillator1Shape.setValue(getOnOffButton(), (byte) 0);
		EventProcessorMidiMessage result = EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(oscillator1Shape);
		return result;
	}

	public boolean isSawtooth() {
		final byte value = programParameter.getValue();
		final boolean isSawtooth = 1 == value;
		return isSawtooth;
	}

	public void switchSawtoothButtonOn() {
		programParameter.setAbsoluteValue((byte) 1);
	}

	public void switchTriangleButtonOn() {
		programParameter.setAbsoluteValue((byte) 2);
	}

	public boolean isTriangle() {
		final byte value = programParameter.getValue();
		final boolean isTriangle = 2 == value;
		return isTriangle;
	}

	public boolean isSquare() {
		final byte value = programParameter.getValue();
		final boolean isSquare = 3 == value;
		return isSquare;
	}
}
