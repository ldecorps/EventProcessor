package decorps.eventprocessor.vendors.livid;

import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class Button implements Controller {

	public final byte id;
	private ProgramParameter programParameter = ProgramParameter.nullParameter;

	public boolean isSwitchedOn() {
		return 0x7f == programParameter.getRebasedValue();
	}

	public void switchOn() {
		programParameter.setValue(this, (byte) 0x7f);
	}

	public void switchOff() {
		programParameter.setValue(this, (byte) 0);
	}

	public void flip() {
		if (isSwitchedOn())
			switchOff();
		else
			switchOn();
	}

	public Button() {
		id = BankLayout.nextButtonId++;
		programParameter = ProgramParameter.nullParameter;
	}

	public byte getId() {
		return id;
	}

	public void setValue(byte value) {
		programParameter.setValue(this, value);
	}

	public byte getRebasedValue() {
		return (byte) (0 == programParameter.getValue() ? 0 : 64);
	}

	public byte getValue() {
		return programParameter.getValue();
	}

	public Mode getMode() {
		return Mode.Absolute;
	}

	public int getCCOrNoteNumber() {
		if (id < 8)
			return id * 4 + 1;
		if (id < 16)
			return id * 4 - 30;
		if (id < 24)
			return id * 4 - 61;
		return id * 4 - 92;
	}

	public ProgramParameter getProgramParameter() {
		return programParameter;
	}

	public void setProgramParameter(ProgramParameter programParameter) {
		this.programParameter = programParameter;
	}

	public static EventProcessorMidiMessage switchButtonOff(Button button) {
		final int ccOrNoteNumber = button.getCCOrNoteNumber();
		final EventProcessorMidiMessage buildShortMessage = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.NOTE_OFF, 0, ccOrNoteNumber, 0);
		return buildShortMessage;
	}

	@Override
	public String toString() {
		return "Button [id=" + id + ", programParameter=" + programParameter
				+ "]";
	}

	public boolean isAbsolute() {
		return true;
	}

	public boolean isButton() {
		return true;
	}

	public static int getIdForCc(int ccNumberOrNote) {
		int multipleOfFour = (ccNumberOrNote - 1) / 4;

		return (ccNumberOrNote - 1 - 4 * multipleOfFour) * 8 + 1
				* multipleOfFour;
	}
}