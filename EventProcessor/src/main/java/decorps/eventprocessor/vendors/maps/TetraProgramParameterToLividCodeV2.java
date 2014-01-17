package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;
import decorps.eventprocessor.vendors.livid.LividCodev2Map;

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
					map.controllerNumber, abstractProgramParameter.getValue());
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
		// http://docs.oracle.com/javase/tutorial/java/nutsandbolts/op3.html
		byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0, b6 = 0, b7 = 0, b8 = 0;
		b1 = (byte) ((programParameterData.currentLayer().oscillator1Shape
				.getValue() != 0) ? 1 : 0);

		return EventProcessorSysexMessage.build(LividCodev2Map
				.buildSet_all_LED_indicators(b1, b2, b3, b4, b5, b6, b7, b8));
	}
}
