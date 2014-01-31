package decorps.eventprocessor.vendors.livid;

public class Button {
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
}