package decorps.eventprocessor.vendors.livid;

public class Button implements Controller {
	private boolean value;

	public boolean isSwitchedOn() {
		return value;
	}

	public void switchOn() {
		value = true;
	}

	public void switchOff() {
		value = false;
	}

	public void flip() {
		value = !value;
	}

	public Button() {
	}

	@Override
	public void setValue(byte value) {
		this.value = value == 0;
	}

	@Override
	public byte getRebasedValue() {
		return (byte) (value ? 1 : 0);
	}

	@Override
	public byte getValue() {
		return getRebasedValue();
	}
}