package decorps.eventprocessor.vendors.dsi.programparameters;

import decorps.eventprocessor.utils.BaseUtils;

public abstract class AbstractProgramParameter implements HasCcValue {
	public final byte data;

	protected AbstractProgramParameter(byte b) {
		this.data = b;
	}

	@Override
	public String toString() {
		return "[parameter=" + getClass().getSimpleName() + ", byte="
				+ BaseUtils.byteToHexa(data) + "]";
	}

	@Override
	public byte getRebasedValue() {
		if (ZeroTo120Range.class.isAssignableFrom(getClass()))
			return (byte) (data * (127d / 120d));
		else if (ZeroTo100Range.class.isAssignableFrom(getClass()))
			return (byte) (data * (127d / 100d));
		final byte result = (byte) (data & 0x7F);
		return result;
	}
}
