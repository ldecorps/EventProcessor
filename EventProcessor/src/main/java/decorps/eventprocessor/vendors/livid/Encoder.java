package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class Encoder {
	private byte value;
	private Mode mode;
	private AbstractProgramParameter programParameter;

	public AbstractProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(AbstractProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public Encoder() {
		value = 0;
		mode = Mode.Absolute;
	}

}