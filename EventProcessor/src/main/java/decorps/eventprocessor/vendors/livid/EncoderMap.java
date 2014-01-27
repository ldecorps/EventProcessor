package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class EncoderMap extends AbstractMap {

	public EncoderMap(
			Class<? extends AbstractProgramParameter> abstractProgramParameterClass,
			int bank, int controllerNumber) {
		super(abstractProgramParameterClass, bank, controllerNumber);
	}

}
