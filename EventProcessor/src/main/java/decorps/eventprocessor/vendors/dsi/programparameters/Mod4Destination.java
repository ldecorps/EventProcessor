package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod4Destination extends ProgramParameter implements ZeroTo47Range {

	public Mod4Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 76;
	}

}
