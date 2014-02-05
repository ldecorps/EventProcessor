package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class Button implements Controller {

	private ProgramParameter programParameter = ProgramParameter.nullParameter;
	public final byte id;

	public boolean isSwitchedOn() {
		return 0x7f == programParameter.getRebasedValue();
	}

	public void switchOn() {
		programParameter.setValue((byte) 0x7f);
	}

	public void switchOff() {
		programParameter.setValue((byte) 0);
	}

	public void flip() {
		if (isSwitchedOn())
			switchOff();
		else
			switchOn();
	}

	public Button() {
		id = BankLayout.nextButtonId++;
	}

	public byte getId() {
		return id;
	}

	public void setValue(byte value) {
		programParameter.setValue(value);
	}

	public byte getRebasedValue() {
		return programParameter.getRebasedValue();
	}

	public byte getValue() {
		return programParameter.getValue();
	}

	public Mode getMode() {
		return Mode.Relative;
	}

	public int getCCOrNoteNumber() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public ProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(ProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

}