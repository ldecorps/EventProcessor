package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static decorps.eventprocessor.utils.BaseUtils.bytesToInts;

public class BankLayout {
	final boolean[] buttons = new boolean[45];
	final byte[] encoders = new byte[32];
	public static BankLayout CurrentBank = new BankLayout();

	public byte[] getButtonsAsByteArrays() {
		byte[] result = new byte[8];
		String binaryRepresentation = buildBinaryRepresentation(buttons);
		for (int i = 0; i < 7 && (1 + i) * 8 < buttons.length; i++) {
			result[i] = binaryToByte(binaryRepresentation.substring((i * 8),
					(1 + i) * 8));
		}
		return result;
	}

	private String buildBinaryRepresentation(boolean[] buttonValues) {
		String result = "";
		for (boolean buttonValue : buttonValues)
			result += buttonValue ? "1" : "0";
		return result;
	}

	public int[] getButtonsAsArrayOfInts() {
		return bytesToInts(getButtonsAsByteArrays());
	}

	public void setFirstColumnOff() {
		buttons[0] = false;
		buttons[1] = false;
		buttons[2] = false;
		buttons[3] = false;
	}

	public void turnOn(int buttonNumber) {
		buttons[buttonNumber - 1] = true;
	}

	public void turnOff(int buttonNumber) {
		buttons[buttonNumber - 1] = false;
	}

	public void setEncoderValue(int encoderNumber, byte value) {
		encoders[encoderNumber - 1] = value;
	}

	public int[] getEncodersAsArrayOfInts() {
		int[] result = new int[64];
		for (int i = 0; i < encoders.length; i++) {
			byte leds_1_to_7 = (byte) (encoders[i] & 0x00001111);
			byte leds_8_to_13 = (byte) (encoders[i] & 0x11110000);
			result[(i * 2)] = leds_1_to_7;
			result[(i * 2) + 1] = leds_8_to_13;
		}
		return result;
	}
}
