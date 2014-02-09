package decorps.eventprocessor.vendors.dsi.programparameters;


public class KeyboardMode extends ProgramParameter implements ZeroTo2Range {

	public KeyboardMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 119;
	}

}
