package decorps.eventprocessor.vendors.dsi.programparameters;

public class KeyMode extends ProgramParameter implements ZeroTo5Range {

	public KeyMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 95;
	}

}
