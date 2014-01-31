package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.utils.BaseUtils.*;
import decorps.eventprocessor.utils.BaseUtils;

public class BankLayout {
	public final Button[] buttons = new Button[45];
	public final Encoder[] encoders = new Encoder[32];
	public static final BankLayout[] AllBanks = createFourBanks();
	public static BankLayout CurrentBank = AllBanks[0];

	public static BankLayout[] createFourBanks() {
		BankLayout[] allBanks = new BankLayout[4];
		for (int i = 0; i < allBanks.length; i++)
			allBanks[i] = new BankLayout(i);
		return allBanks;
	}

	public final int bankNumber;

	public BankLayout(int bankNumber) {
		this.bankNumber = bankNumber;
		for (int i = 0; i < buttons.length; i++)
			buttons[i] = new Button();
		for (int i = 0; i < encoders.length; i++)
			encoders[i] = new Encoder();
	}

	public byte[] getButtonsAsByteArrays() {
		byte[] result = new byte[8];
		String binaryRepresentation = buildBinaryRepresentation(buttons);
		for (int i = 0; i < 7 && (1 + i) * 8 < buttons.length; i++) {
			final String representation = binaryRepresentation.substring(
					(i * 8), (1 + i) * 8);
			result[i] = binaryToByte(BaseUtils.reverseString(representation));
		}
		return result;
	}

	private String buildBinaryRepresentation(Button[] buttons) {
		String result = "";
		for (Button button : buttons)
			result += button.isSwitchedOn() ? "1" : "0";
		return result;
	}

	public int[] getButtonsAsArrayOfInts() {
		return bytesToInts(getButtonsAsByteArrays());
	}

	public void switchFirstColumnOff() {
		buttons[0].switchOff();
		buttons[1].switchOff();
		buttons[2].switchOff();
		buttons[3].switchOff();
	}

	public void turnOn(int buttonNumber) {
		buttons[buttonNumber - 1].switchOn();
	}

	public void turnOff(int buttonNumber) {
		buttons[buttonNumber - 1].switchOff();
	}

	public void setEncoderValue(int encoderNumber, byte value) {
		encoders[encoderNumber - 1].setValue(value);
	}

	public int[] getEncodersAsArrayOfInts() {
		int[] result = new int[64];
		for (int i = 0; i < encoders.length; i++) {
			byte leds_1_to_7 = (byte) (encoders[i].getValue() & 0x00001111);
			byte leds_8_to_13 = (byte) (encoders[i].getValue() & 0x11110000);
			result[(i * 2)] = leds_1_to_7;
			result[(i * 2) + 1] = leds_8_to_13;
		}
		return result;
	}

	public int getEncoderValue(int encoderNumber) {
		return encoders[encoderNumber].getValue();
	}

	public void setButtonOn(int i) {
		buttons[i - 1].switchOn();
	}

	public boolean isButtonSwitchedOff(int i) {
		return false == buttons[i - 1].isSwitchedOn();
	}

	public void setButtonOff(int i) {
		buttons[i - 1].switchOff();
	}

	public boolean areSwitchedOn(int... i) {
		for (int j : i)
			if (isButtonSwitchedOff(j))
				return false;
		return true;
	}

	public void setButtonsOn(int... i) {
		for (int j : i)
			setButtonOn(j);
	}

}
