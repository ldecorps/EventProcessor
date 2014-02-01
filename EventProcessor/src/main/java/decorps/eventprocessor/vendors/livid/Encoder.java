package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ZeroTo127Range;

public class Encoder implements Controller {
	public final byte id;
	private AbstractProgramParameter programParameter = AbstractProgramParameter.nullParameter;

	public AbstractProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(AbstractProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

	public Mode getMode() {
		if (null == programParameter
				|| programParameter instanceof ZeroTo127Range
				|| AbstractProgramParameter.nullParameter
						.equals(programParameter))
			return Mode.Absolute;
		return Mode.Relative;
	}

	@Override
	public byte getRebasedValue() {
		return programParameter.getRebasedValue();
	}

	@Override
	public void setValue(byte value) {
		programParameter.setValue(value);
	}

	public Encoder() {
		id = BankLayout.nextEncodeId++;
	}

	@Override
	public byte getValue() {
		return programParameter.getValue();
	}

}