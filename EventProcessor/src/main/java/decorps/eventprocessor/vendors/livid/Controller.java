package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ValueRange;

public interface Controller extends ValueRange {

	void setValue(byte value);

	byte getRebasedValue();

	byte getValue();

	byte getId();

	ProgramParameter getProgramParameter();

	public Mode getMode();

	int getCCOrNoteNumber();

	void setProgramParameter(ProgramParameter programParameter);

	boolean isAbsolute();

	boolean isButton();

	public Encoder asEncoder();

	public Button asButton();

	public int getLedRingCc();

}
