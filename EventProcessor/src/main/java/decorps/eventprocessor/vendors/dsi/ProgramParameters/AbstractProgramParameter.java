package decorps.eventprocessor.vendors.dsi.ProgramParameters;

import decorps.eventprocessor.utils.BaseUtils;

public abstract class AbstractProgramParameter {
	byte data;

	protected AbstractProgramParameter(byte b) {
		this.data = b;
	}

	@Override
	public String toString() {
		return "[byte=" + BaseUtils.byteToHexa(data) + "]";
	}
}
