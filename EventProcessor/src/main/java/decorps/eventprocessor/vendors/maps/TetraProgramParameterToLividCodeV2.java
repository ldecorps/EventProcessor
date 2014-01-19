package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class TetraProgramParameterToLividCodeV2 implements EventProcessorMap {

	class Map {
		public final Class<? extends AbstractProgramParameter> abstractProgramParameterClass;
		public final byte bank;
		public final byte controllerNumber;

		public Map(
				Class<? extends AbstractProgramParameter> abstractProgramParameterClass,
				int bank, int controllerNumber) {
			super();
			this.abstractProgramParameterClass = abstractProgramParameterClass;
			this.bank = (byte) bank;
			this.controllerNumber = (byte) controllerNumber;
		}
	}

	Map[] mapping = new Map[] { new Map(Oscillator1Frequency.class, 0, 0) };

	@Override
	public LividCodeEventProcessorCCShortMessage map(
			AbstractProgramParameter abstractProgramParameter) {
		for (Map map : mapping) {
			return new LividCodeEventProcessorCCShortMessage(map.bank,
					map.controllerNumber,
					abstractProgramParameter.getRebasedValue());
		}

		throw new EventProcessorException(abstractProgramParameter.getClass()
				.getSimpleName() + " not supported yet");
	}

	@Override
	public LividCodeEventProcessorCCShortMessageComposite mapToCcs(
			ProgramParameterData programParameterData) {
		LividCodeEventProcessorCCShortMessageComposite result = new LividCodeEventProcessorCCShortMessageComposite();
		for (AbstractProgramParameter abstractProgramParameter : programParameterData
				.getAllAbstractProgramParameters()) {
			result.add(map(abstractProgramParameter));
		}
		return result;
	}

	@Override
	public EventProcessorMidiMessageComposite map(
			ProgramParameterData programparameterdata) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public EventProcessorMidiMessage mapToSetAllLedIndicators(
			ProgramParameterData programParameterData) {

		mapOscillator1Shape(programParameterData);

		byte[] Set_all_LED_indicators = LividMessageFactory
				.buildSet_all_LED_indicators(
						BankLayout.CurrentBank.getButtonsAsArrayOfInts())
				.getMessage();
		return EventProcessorMidiMessage.build(Set_all_LED_indicators);
	}

	private void mapOscillator1Frequency(
			ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(1, programParameterData
				.currentLayer().oscillator1Frequency.getRebasedValue());
	}

	private void mapOscillator1FineTune(
			ProgramParameterData programParameterData) {
		BankLayout.CurrentBank.setEncoderValue(2, programParameterData
				.currentLayer().oscillator1FineTune.getRebasedValue());
	}

	void mapOscillator1Shape(ProgramParameterData programParameterData) {
		final byte value = programParameterData.currentLayer().oscillator1Shape.data;
		final boolean isOscillator1Off = value == 0;
		BankLayout.CurrentBank.turnOff(1);
		if (isOscillator1Off)
			return;
		final boolean setToSawooth = 1 == value;
		final boolean setToTriangle = 2 == value;
		final boolean setSquare = 3 == value;
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

	public EventProcessorMidiMessage mapToSetLedRingsIndicators(
			ProgramParameterData programParameterData) {

		mapOscillator1Frequency(programParameterData);
		mapOscillator1FineTune(programParameterData);

		byte[] Set_all_LED_indicators = LividMessageFactory
				.buildSet_LED_Ring_indicators(
						BankLayout.CurrentBank.getEncodersAsArrayOfInts())
				.getMessage();
		return EventProcessorMidiMessage.build(Set_all_LED_indicators);
	}
}
