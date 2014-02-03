package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Glide;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class TetraProgramParameterToLividCodeV2 implements EventProcessorMap {

	public static final DefaultMap[] mapping = new DefaultMap[] {
	// new EncoderMap(Osc1Frequency.class, 0, 1),
	// new EncoderMap(Osc1FineFreq.class, 0, 2),
	// new EncoderMap(Oscillator1Shape.class, 0, 3),
	// new EncoderMap(Oscillator1Glide.class, 0, 4),
	// new ButtonMap(Osc1KeyTrack.class, 0, 12),
	// new EncoderMap(Osc2Frequency.class, 0, 5),
	// new EncoderMap(Osc2FineFreq.class, 0, 6),
	// new EncoderMap(Oscillator2Shape.class, 0, 7),
	// new EncoderMap(Oscillator2Glide.class, 0, 8),
	// new ButtonMap(Osc2KeyTrack.class, 0, 16)

	};

	public LividCodeEventProcessorCCShortMessage mapToCC(
			ProgramParameter programParameter) {
		Controller controller = MapRepository.getControllersForParameter(
				programParameter).get(0);

		return new LividCodeEventProcessorCCShortMessage(programParameter,
				controller, programParameter.getRebasedValue());
	}

	public EventProcessorMidiMessage mapToLividSysex(
			ProgramParameterData programParameterData) {
		List<EventProcessorMidiMessage> messages = new ArrayList<EventProcessorMidiMessage>();
		for (EventProcessorMap map : MapRepository.maps) {
			messages.add(map.mapToLividSysex(programParameterData));
		}
		EventProcessorMidiMessage composite = EventProcessorMidiMessageComposite
				.buildComposite(messages
						.toArray(new EventProcessorMidiMessage[] {}));
		return composite;

	}

	private void mapOscillator1FineTune(
			ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(2, programParameterData
				.currentLayer().oscillator1FineTune.getRebasedValue());
	}

	private void mapOscillator1Frequency(
			ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(1, programParameterData
				.currentLayer().oscillator1Frequency.getRebasedValue());
	}

	private void mapOscillator1Glide(ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(3, programParameterData
				.currentLayer().oscillator1Glide.getRebasedValue());
	}

	private void mapOscillator1ShapeButton(
			ProgramParameterData programParameterData) {
		final byte value = programParameterData.currentLayer().oscillator1Shape
				.getValue();
		final boolean isOscillator1Off = value == 0;
		if (isOscillator1Off) {
			BankLayout.CurrentBank.turnOff(1);
			return;
		}
		BankLayout.CurrentBank.turnOn(1);
		final boolean setToSawooth = 1 == value;
		final boolean setToTriangle = 2 == value;
		final boolean setSquare = 2 < value;
		if (setToSawooth) {
			BankLayout.CurrentBank.turnOn(2);
			return;
		} else if (setToTriangle) {
			BankLayout.CurrentBank.turnOn(3);
			return;
		} else if (setSquare) {
			BankLayout.CurrentBank.turnOn(4);
			return;
		}

	}

	private void mapOscillator1ShapePulseWidth(
			ProgramParameterData programParameterData) {
		final byte data = programParameterData.currentLayer().oscillator1Shape
				.getValue();
		if (data <= 3)
			return;
		byte rebasedPulseWidthValues = (byte) (((data - 4.0) / 100d) * 127d);
		BankLayout.CurrentBank.setEncoderValue(4, rebasedPulseWidthValues);
	}

	public EventProcessorMidiMessage mapToSetAllLedIndicators(
			ProgramParameterData programParameterData) {

		mapOscillator1ShapeButton(programParameterData);
		mapOscillator1Keyboard(programParameterData);
		mapOscillator2ShapeButton(programParameterData);
		mapOscillator2Keyboard(programParameterData);

		byte[] Set_all_LED_indicators = LividMessageFactory
				.buildSet_all_LED_indicators(
						BankLayout.CurrentBank.getButtonsAsArrayOfInts())
				.getMessage();
		return EventProcessorMidiMessage.build(Set_all_LED_indicators);
	}

	private void mapOscillator1Keyboard(
			ProgramParameterData programParameterData) {
		final byte value = programParameterData.currentLayer().oscillator1Keyboard
				.getValue();
		if (0 == value)
			BankLayout.CurrentBank.turnOff(12);
		else
			BankLayout.CurrentBank.turnOn(12);
	}

	public EventProcessorMidiMessage mapToSetLedRingsIndicators(
			ProgramParameterData programParameterData) {

		mapProgramParameterData(programParameterData);

		byte[] Set_all_LED_indicators = LividMessageFactory
				.buildSet_LED_Ring_indicators(
						BankLayout.CurrentBank.getEncodersAsArrayOfInts())
				.getMessage();
		return EventProcessorMidiMessage.build(Set_all_LED_indicators);
	}

	public void mapProgramParameterData(
			ProgramParameterData programParameterData) {
		mapOscillator1Frequency(programParameterData);
		mapOscillator1FineTune(programParameterData);
		mapOscillator1ShapePulseWidth(programParameterData);
		mapOscillator1Glide(programParameterData);
		mapOscillator2Frequency(programParameterData);
		mapOscillator2FineTune(programParameterData);
		mapOscillator2ShapePulseWidth(programParameterData);
		mapOscillator2Glide(programParameterData);
	}

	public EventProcessorMidiMessage mapToSetAllEncoderValues(
			ProgramParameterData programParameterData) {
		mapProgramParameterData(programParameterData);
		int[] encoderValues = new int[32];
		for (int i = 0; i < encoderValues.length; i++)
			encoderValues[i] = getValueForEncoder(programParameterData, i);
		return LividMessageFactory.buildSet_Encoder_Values(encoderValues);
	}

	private int getValueForEncoder(ProgramParameterData programParameterData,
			int encoderNumber) {
		return BankLayout.CurrentBank.getEncoderValue(encoderNumber);
	}

	public EventProcessorMidiMessage map(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	private void mapOscillator2FineTune(
			ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(6, programParameterData
				.currentLayer().oscillator2FineTune.getRebasedValue());
	}

	private void mapOscillator2Frequency(
			ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(5, programParameterData
				.currentLayer().oscillator1Frequency.getRebasedValue());
	}

	private void mapOscillator2Glide(ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(7, programParameterData
				.currentLayer().oscillator1Glide.getRebasedValue());
	}

	private void mapOscillator2Keyboard(
			ProgramParameterData programParameterData) {
		final byte value = programParameterData.currentLayer().oscillator2Keyboard
				.getValue();
		if (0 == value)
			BankLayout.CurrentBank.turnOff(16);
		else
			BankLayout.CurrentBank.turnOn(16);
	}

	private void mapOscillator2ShapeButton(
			ProgramParameterData programParameterData) {
		final byte value = programParameterData.currentLayer().oscillator2Shape
				.getValue();
		final boolean isOscillator2Off = value == 0;
		if (isOscillator2Off) {
			BankLayout.CurrentBank.turnOff(5);
			return;
		}
		BankLayout.CurrentBank.turnOn(5);
		final boolean setToSawooth = 1 == value;
		final boolean setToTriangle = 2 == value;
		final boolean setSquare = 2 < value;
		if (setToSawooth) {
			BankLayout.CurrentBank.turnOn(6);
			return;
		} else if (setToTriangle) {
			BankLayout.CurrentBank.turnOn(7);
			return;
		} else if (setSquare) {
			BankLayout.CurrentBank.turnOn(8);
			return;
		}

	}

	private void mapOscillator2ShapePulseWidth(
			ProgramParameterData programParameterData) {
		final byte data = programParameterData.currentLayer().oscillator2Shape
				.getValue();
		if (data <= 3)
			return;
		byte rebasedPulseWidthValues = (byte) (((data - 4.0) / 100d) * 127d);
		BankLayout.CurrentBank.setEncoderValue(8, rebasedPulseWidthValues);
	}

	public void applyMapping() {
		BankLayout.CurrentBank.encoders[0]
				.setProgramParameter(new Oscillator1Glide(0, (byte) 0));
	}

	public ProgramParameter getProgramParameter() {
		return null;
	}

	public List<Controller> getControllers() {
		return null;
	}

	public void map(Controller controller) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void map(ProgramParameter programParameter) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void refreshControllers() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void refreshProgramParameter() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public EventProcessorMidiMessage mapToTetraNRPN(
			ProgramParameterData programParameterData) {
		List<EventProcessorMidiMessage> messages = new ArrayList<EventProcessorMidiMessage>();
		for (EventProcessorMap map : MapRepository.maps) {
			messages.add(map.mapToTetraNRPN(programParameterData));
		}
		EventProcessorMidiMessage composite = EventProcessorMidiMessageComposite
				.buildComposite(messages
						.toArray(new EventProcessorMidiMessage[] {}));
		return composite;
	}

	public EventProcessorMidiMessage mapToLividCc() {
		List<EventProcessorMidiMessage> messages = new ArrayList<EventProcessorMidiMessage>();
		for (EventProcessorMap map : MapRepository.maps) {
			messages.add(map.mapToLividCc());
		}
		EventProcessorMidiMessage composite = EventProcessorMidiMessageComposite
				.buildComposite(messages
						.toArray(new EventProcessorMidiMessage[] {}));
		return composite;
	}
}
