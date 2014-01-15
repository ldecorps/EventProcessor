package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

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
	public LividCodeEventProcessorCCShortMessageComposite map(
			ProgramParameterData programParameterData) {
		LividCodeEventProcessorCCShortMessageComposite result = new LividCodeEventProcessorCCShortMessageComposite();
		for (AbstractProgramParameter abstractProgramParameter : programParameterData
				.getAllAbstractProgramParameters()) {
			result.add(map(abstractProgramParameter));
		}
		return result;
	}

}
