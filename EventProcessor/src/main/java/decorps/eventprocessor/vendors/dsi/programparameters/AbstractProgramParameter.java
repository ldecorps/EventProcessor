package decorps.eventprocessor.vendors.dsi.programparameters;

import decorps.eventprocessor.utils.BaseUtils;

public abstract class AbstractProgramParameter implements HasCcValue {
	public static final AbstractProgramParameter nullParameter = new AbstractProgramParameter(
			0, (byte) 0) {

		@Override
		public byte getLayerANRPNNumber() {
			return 0;
		}
	};
	private byte value;
	public final int position;

	protected AbstractProgramParameter(int number, byte b) {
		this.position = number;
		this.value = b;
	}

	@Override
	public String toString() {
		return "[parameter=" + getClass().getSimpleName() + ", byte="
				+ BaseUtils.byteToHexa(value) + "]";
	}

	@Override
	public byte getRebasedValue() {
		byte result = value;
		if (ZeroTo120Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 120d));
		else if (ZeroOrOneRange.class.isAssignableFrom(getClass()))
			result = (byte) ((byte) 0x7f & (value * 127));
		else if (FourTo103Range.class.isAssignableFrom(getClass()))
			result = (byte) ((value - 4) * (127d / 100d));
		else if (ZeroTo3Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 3d));
		else if (ZeroTo5Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 5d));
		else if (ZeroTo100Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 100d));
		else if (ZeroTo254Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 254d));

		result = (byte) (result & 0x7F);
		return result;
	}

	public abstract byte getLayerANRPNNumber();

	public void setValue(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
