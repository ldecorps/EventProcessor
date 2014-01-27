package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class ButtonMap extends AbstractMap {

	public ButtonMap(
			Class<? extends AbstractProgramParameter> abstractProgramParameterClass,
			int bank, int controllerNumber) {
		super(abstractProgramParameterClass, bank, controllerNumber);
	}

}
