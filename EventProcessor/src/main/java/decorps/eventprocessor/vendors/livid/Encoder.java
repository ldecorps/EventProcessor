package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.vendors.livid.BankLayout.CurrentBank;
import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.Centered;
import decorps.eventprocessor.vendors.dsi.programparameters.Power;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Spread;
import decorps.eventprocessor.vendors.dsi.programparameters.ZeroTo127Range;
import decorps.eventprocessor.vendors.livid.messages.Set_LED_Ring_Style;

public class Encoder implements Controller {
	public final byte id;
	private ProgramParameter programParameter = ProgramParameter.nullParameter;
	public final BankLayout bankLayout;

	public ProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(ProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

	public Mode getMode() {
		if (null == programParameter
				|| programParameter instanceof ZeroTo127Range
				|| ProgramParameter.nullParameter.equals(programParameter))
			return Mode.Absolute;
		return Mode.Relative;
	}

	public byte getRebasedValue() {
		return programParameter.getRebasedValue();
	}

	public void setValue(byte value) {
		programParameter.setValue(this, value);
	}

	@Override
	public String toString() {
		return "Encoder [id=" + id + ", programParameter=" + programParameter
				+ "]";
	}

	public byte getValue() {
		return programParameter.getValue();
	}

	public Encoder() {
		id = BankLayout.nextEncoderId++;
		bankLayout = CurrentBank;
		programParameter = ProgramParameter.nullParameter;
	}

	public byte getId() {
		return id;
	}

	public int getCCOrNoteNumber() {
		final byte encoderId = id;
		return getNoteOrCcForEncoderId(encoderId);
	}

	public static int getNoteOrCcForEncoderId(final byte encoderId) {
		if (encoderId < 8)
			return encoderId * 4 + 1;
		if (encoderId < 16)
			return encoderId * 4 - 30;
		if (encoderId < 24)
			return encoderId * 4 - 61;
		return encoderId * 4 - 92;
	}

	public int getEncoderStyle() {
		if (programParameter instanceof Power)
			return Set_LED_Ring_Style.FILL;
		if (programParameter instanceof Centered)
			return Set_LED_Ring_Style.EQ;
		if (programParameter instanceof Spread)
			return Set_LED_Ring_Style.SPREAD;
		return Set_LED_Ring_Style.WALK;
	}

	public boolean isAbsolute() {
		return programParameter.isAbsolute();
	}

	public boolean isButton() {
		return false;
	}

	public static int getIdForCc(int ccNumberOrNote) {
		int multipleOfFour = (ccNumberOrNote - 1) / 4;

		return (ccNumberOrNote - 1 - 4 * multipleOfFour) * 8 + 1
				* multipleOfFour;
	}

	public void incrementUntil(byte upperLimit) {
		while (programParameter.getValue() != upperLimit)
			programParameter.incrementValue();
	}

	public void decrementUntil(int lowerLimit) {
		while (programParameter.getValue() != lowerLimit)
			programParameter.decrementValue();
	}

	public static int getLedRingIdForEncoder(byte encoderId) {
		return encoderId + 33;
	}

	public Encoder asEncoder() {
		return this;
	}

	public Button asButton() {
		throw new EventProcessorException("Encoder is not an encoder");
	}

	public int getLedRingCc() {
		final int ccNumber = getNoteOrCcForEncoderId(id);
		final int ledRingCc = ccNumber + 32;
		return ledRingCc;
	}
}