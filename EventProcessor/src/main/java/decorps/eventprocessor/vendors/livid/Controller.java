package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public interface Controller {

	void setValue(byte value);

	byte getRebasedValue();

	byte getValue();

	byte getId();

	ProgramParameter getProgramParameter();

	public Mode getMode();

	int getCCOrNoteNumber();

	void setProgramParameter(ProgramParameter programParameter);

}
