package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ZeroTo127Range;

public class Encoder implements Controller {
	public final byte id;
	private byte value;
	private AbstractProgramParameter programParameter;

	public AbstractProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(AbstractProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

	public Mode getMode() {
		if (null == programParameter
				|| programParameter instanceof ZeroTo127Range)
			return Mode.Absolute;
		return Mode.Relative;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public Encoder() {
		value = 0;
		id = BankLayout.nextEncodeId++;
	}

}