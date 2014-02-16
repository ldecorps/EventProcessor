package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.utils.BaseUtils.*;
import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.utils.BaseUtils;
import decorps.eventprocessor.vendors.dsi.ParameterFactory;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class BankLayout {
	public final Button[] buttons = new Button[45];
	public final Encoder[] encoders = new Encoder[32];
	public static final BankLayout[] AllBanks = createFourBanks();
	public static BankLayout CurrentBank = AllBanks[0];
	public static BankLayout Bank1 = AllBanks[0];
	public static BankLayout Bank2 = AllBanks[1];
	public static BankLayout Bank3 = AllBanks[2];
	public static BankLayout Bank4 = AllBanks[3];
	public static byte nextEncoderId;
	public static byte nextButtonId;
	public static ProgramParameterData programParameterData;
	public final int bankNumber;

	public static BankLayout[] createFourBanks() {
		BankLayout[] allBanks = new BankLayout[4];
		for (int i = 0; i < allBanks.length; i++)
			allBanks[i] = new BankLayout(i);
		return allBanks;
	}

	public BankLayout(int bankNumber) {
		this.bankNumber = bankNumber;
		initialiseControllers();
	}

	public void initialiseControllers() {
		nextEncoderId = 0;
		nextButtonId = 0;
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
		if (1 > encoderNumber)
			throw new EventProcessorException("Encoder index starts at 1");
		encoders[encoderNumber - 1].setValue(value);
	}

	public int[] getEncodersAsArrayOfInts() {
		int[] result = new int[64];
		for (int i = 0; i < encoders.length; i++) {
			byte leds_1_to_7 = (byte) (encoders[i].getRebasedValue() & 0x00001111);
			byte leds_8_to_13 = (byte) (encoders[i].getRebasedValue() & 0x11110000);
			result[(i * 2)] = leds_1_to_7;
			result[(i * 2) + 1] = leds_8_to_13;
		}
		return result;
	}

	public int getEncoderValue(int encoderNumber) {
		return encoders[encoderNumber].getRebasedValue();
	}

	public void switchButtonOn(int i) {
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

	public void switchButtonsOn(int... i) {
		for (int j : i)
			switchButtonOn(j);
	}

	public int[] getEncoderModes() {
		int[] result = new int[8];
		for (int i = 0; i < 32; i = i + 8) {
			final int index = i / 8;
			result[index] = getNextSevenEncoderModes(i);
			result[index + 1] = getEncoderMode(i + 7);
		}
		return result;
	}

	public int[] getEncoderSyles() {
		int[] result = new int[32];
		for (int i = 0; i < 32; i = i + 1) {
			result[i] = encoders[i].getEncoderStyle();
		}
		return result;
	}

	private int getEncoderMode(int encoderIndex) {
		final BankLayout currentBank = BankLayout.CurrentBank;
		final Encoder encoder = currentBank.encoders[encoderIndex];
		return encoder.getMode() == Mode.Absolute ? 0 : 1;
	}

	private int getNextSevenEncoderModes(int encoderIndex) {
		String result = "";
		for (int i = 0; i < 7; i++) {
			result = getEncoderMode(encoderIndex + i) + result;
		}
		return binaryToByte(result);
	}

	public Encoder getEncoderForCc(int ccNumber) {
		return encoders[Encoder.getIdForCc(ccNumber)];
	}

	public static ProgramParameterData getCurrentProgramParameterData() {
		return programParameterData;
	}

	public int[] getEncoderValues() {
		int[] result = new int[32];
		for (int i = 0; i < 32; i++)
			result[i] = encoders[i].getRebasedValue();
		return result;
	}

	public static void selectBank(int i) {
		CurrentBank = BankLayout.AllBanks[i];
	}

	public static BankLayout getCurrentBank() {
		return CurrentBank;
	}

	public static ProgramParameter getCurrentProgramParameter(
			Class<? extends ProgramParameter> programParameterClass) {
		return ParameterFactory
				.getCurrentProgramParameterForClass(programParameterClass);
	}

}
