package decorps.eventprocessor.vendors.dsi.programparameters;

import decorps.eventprocessor.utils.BaseUtils;
import decorps.eventprocessor.vendors.livid.Controller;

public abstract class ProgramParameter implements ValueRange {
	public static final ProgramParameter nullParameter = new ProgramParameter(
			-1, (byte) 0x0) {

		@Override
		public byte getLayerANRPNNumber() {
			return -1;
		}
	};
	private byte value;
	public final int position;

	protected ProgramParameter(int number, byte b) {
		this.position = number;
		this.value = b;
	}

	@Override
	public String toString() {
		return "[parameter=" + getClass().getSimpleName() + ", byte=0x"
				+ BaseUtils.byteToHexa(value) + "]";
	}

	public byte getRebasedValue() {
		byte result = value;
		if (ZeroTo120Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 120d));
		else if (ZeroOrOneRange.class.isAssignableFrom(getClass()))
			result = (byte) ((byte) 0x7f & (value * 127));
		else if (FourTo103Range.class.isAssignableFrom(getClass()))
			result = (byte) ((value - 4) * (127d / 100d));
		else if (ZeroTo2Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 2d));
		else if (ZeroTo3Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 3d));
		else if (ZeroTo4Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 4d));
		else if (ZeroTo5Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 5d));
		else if (ZeroTo12Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 12d));
		else if (ZeroTo14Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 14d));
		else if (ZeroTo20Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 20d));
		else if (ZeroTo43Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 43d));
		else if (ZeroTo47Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 47d));
		else if (ZeroTo100Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 100d));
		else if (ZeroTo164Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 164d));
		else if (ZeroTo166Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 166d));
		else if (ZeroTo183Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 183d));
		else if (ZeroTo254Range.class.isAssignableFrom(getClass()))
			result = (byte) (value * (127d / 254d));
		else if (ThirtyTo250Range.class.isAssignableFrom(getClass()))
			result = (byte) ((value - 30) * (127d / 221d));

		result = (byte) (result & 0x7F);
		return result;
	}

	public abstract byte getLayerANRPNNumber();

	public void setValue(Controller controller, byte value) {
		if (controller.isButton()) {
			this.value = (byte) newValue(value);
		} else if (controller.isAbsolute())
			this.value = value;
		else {
			if (127 == value)
				incrementValue();
			else
				decrementValue();
		}
	}

	private int newValue(byte value) {
		final boolean isSwitchOff = value == 0;
		final int newValue = isSwitchOff ? 0 : 64;
		return newValue;
	}

	public byte getValue() {
		return value;
	}

	public boolean isAbsolute() {
		boolean absolute = this instanceof ZeroTo127Range;
		return absolute;
	}

	public void incrementValue() {
		if (getRebasedValue() < 127)
			value++;
	}

	public void decrementValue() {
		if (getRebasedValue() > 0)
			value--;
	}

	public void setAbsoluteValue(byte value) {
		this.value = value;
	}

}
