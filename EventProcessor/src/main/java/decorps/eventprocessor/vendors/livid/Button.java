package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

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

	public void setValue(byte value) {
		this.value = value == 0;
	}

	public byte getRebasedValue() {
		return (byte) (value ? 1 : 0);
	}

	public byte getValue() {
		return getRebasedValue();
	}

	public byte getId() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public ProgramParameter getProgramParameter() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public Mode getMode() {
		return Mode.Relative;
	}

	public int getCCNumber() {
		throw new EventProcessorException("Not Implemented Yet");
	}

}