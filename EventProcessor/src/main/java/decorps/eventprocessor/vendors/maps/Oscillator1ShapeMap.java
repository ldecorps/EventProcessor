package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.Layer;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Button;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.Encoder;

public class Oscillator1ShapeMap extends DefaultControllerParameterMap {

	public Oscillator1ShapeMap(ProgramParameter programParameter,
			Controller[] controllers) {
		super(programParameter, controllers);
	}

	public Oscillator1ShapeMap() {
		this(registerParameter(), new Controller[] { getSawtoothButton(),
				getTriangleButton(), getSawtoothTriangleMixButton(),
				getSquareButton(), getPulseWidthEncoder() });
	}

	private static Encoder getPulseWidthEncoder() {
		return BankLayout.Bank1.encoders[24];
	}

	static Button getSawtoothButton() {
		final Button button = BankLayout.Bank1.buttons[0];
		return button;
	}

	static Button getTriangleButton() {
		final Button button = BankLayout.Bank1.buttons[8];
		return button;
	}

	static Button getSawtoothTriangleMixButton() {
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
		EventProcessorMidiMessage forTetra = null;
		if (0 == newCcValue)
			forTetra = switchOscillatorOff();
		else if (1 == newCcValue)
			forTetra = buttonSawtoothPressed();
		else if (2 == newCcValue)
			forTetra = buttonTrianglePressed();
		else if (3 == newCcValue)
			forTetra = buttonSawtoothTriangleMixPressed();
		else if (4 == newCcValue)
			forTetra = buttonSquarePressed();
		else if (newCcValue > 4)
			forTetra = setPulseWidth(newCcValue);
		return forTetra;
	}

	private EventProcessorMidiMessage setPulseWidth(int newCcValue) {
		programParameter.setValue(getSquareButton(), (byte) newCcValue);
		return buildNRPN(programParameter);
	}

	private EventProcessorMidiMessage buttonSawtoothTriangleMixPressed() {
		if (programParameter.getValue() == 3)
			return switchOscillatorOff();
		programParameter.setValue(getSawtoothTriangleMixButton(), (byte) 3);
		return buildNRPN(programParameter);
	}

	private EventProcessorMidiMessage buttonSawtoothPressed() {
		if (programParameter.getValue() == 1)
			return switchOscillatorOff();
		programParameter.setValue(getSawtoothButton(), (byte) 1);
		return buildNRPN(programParameter);
	}

	private EventProcessorMidiMessage buttonTrianglePressed() {
		if (programParameter.getValue() == 2)
			return switchOscillatorOff();
		programParameter.setValue(getTriangleButton(), (byte) 2);
		return buildNRPN(programParameter);
	}

	private EventProcessorMidiMessage buttonSquarePressed() {
		if (programParameter.getValue() == 4)
			return switchOscillatorOff();
		programParameter.setAbsoluteValue((byte) 4);
		return buildNRPN(programParameter);
	}

	private EventProcessorMidiMessage switchOscillatorOff() {
		programParameter.setAbsoluteValue((byte) 0);
		return buildNRPN(programParameter);
	}

	private EventProcessorMidiMessage buildNRPN(
			final ProgramParameter oscillator1Shape) {
		EventProcessorMidiMessage result = EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(oscillator1Shape);
		return result;
	}

	public boolean isSawtooth() {
		final byte value = programParameter.getValue();
		final boolean isSawtooth = 1 == value;
		return isSawtooth;
	}

	public EventProcessorMidiMessage switchSawtoothButtonOn() {
		programParameter.setAbsoluteValue((byte) 1);
		return buildNRPN(programParameter);
	}

	public EventProcessorMidiMessage switchTriangleButtonOn() {
		programParameter.setAbsoluteValue((byte) 2);
		return buildNRPN(programParameter);
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
