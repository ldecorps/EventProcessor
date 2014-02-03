package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ZeroTo127Range;

public class Encoder implements Controller {
	public final byte id;
	private ProgramParameter programParameter = ProgramParameter.nullParameter;

	public ProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(ProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

	public Mode getMode() {
		if (null == programParameter
				|| programParameter instanceof ZeroTo127Range
				|| ProgramParameter.nullParameter.equals(programParameter))
			return Mode.Absolute;
		return Mode.Relative;
	}

	public byte getRebasedValue() {
		return programParameter.getRebasedValue();
	}

	public void setValue(byte value) {
		programParameter.setValue(value);
	}

	@Override
	public String toString() {
		return "Encoder [id=" + id + ", programParameter=" + programParameter
				+ "]";
	}

	public Encoder() {
		id = BankLayout.nextEncodeId++;
	}

	public byte getValue() {
		return programParameter.getValue();
	}

	public byte getId() {
		return id;
	}

	public int getCCNumber() {
		return id - 1;
	}
}