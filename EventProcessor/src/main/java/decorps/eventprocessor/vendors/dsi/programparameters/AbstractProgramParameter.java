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
		byte result = 0;
		if (ZeroTo120Range.class.isAssignableFrom(getClass()))
			result = (byte) (data * (127d / 120d));
		else if (ZeroTo100Range.class.isAssignableFrom(getClass()))
			result = (byte) (data * (127d / 100d));
		else if (ZeroOrOneRange.class.isAssignableFrom(getClass()))
			result = (byte) ((byte) 0x7f & (data * (byte) 127));
		result = (byte) (result & 0x7F);
		return result;
	}
}
