package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class AbstractMap {

	public final Class<? extends AbstractProgramParameter> abstractProgramParameterClass;
	public final byte bank;
	public final byte controllerNumber;

	public AbstractMap(
			Class<? extends AbstractProgramParameter> abstractProgramParameterClass,
			int bank, int controllerNumber) {
		this.abstractProgramParameterClass = abstractProgramParameterClass;
		this.bank = (byte) bank;
		this.controllerNumber = (byte) controllerNumber;
	}

}