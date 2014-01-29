package decorps.eventprocessor.vendors.dsi.programparameters;

import decorps.eventprocessor.utils.BaseUtils;

public abstract class AbstractProgramParameter implements HasCcValue {
	public final byte data;
	public final int number;

	protected AbstractProgramParameter(int number, byte b) {
		this.number = number;
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
			result = (byte) ((byte) 0x7f & (data * 127));
		else if (FourTo103Range.class.isAssignableFrom(getClass()))
			result = (byte) ((data - 4) * (127d / 100d));
		else if (ZeroTo254Range.class.isAssignableFrom(getClass()))
			result = (byte) (data * (127d / 254d));

		result = (byte) (result & 0x7F);
		return result;
	}

	public abstract byte getNRPNNumber();
}
