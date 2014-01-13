package decorps.eventprocessor.vendors.dsi.programparameters;

import decorps.eventprocessor.utils.BaseUtils;

public abstract class AbstractProgramParameter implements HasCcValue {
	byte data;

	protected AbstractProgramParameter(byte b) {
		this.data = b;
	}

	@Override
	public String toString() {
		return "[parameter=" + getClass().getSimpleName() + ", byte="
				+ BaseUtils.byteToHexa(data) + "]";
	}

	@Override
	public byte getValue() {
		if (ZeroTo120Range.class.isAssignableFrom(getClass()))
			return (byte) (data * (127d / 120d));
		return data;
	}
}
