package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod2Destination extends ProgramParameter implements ZeroTo47Range {

	public Mod2Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 70;
	}

}
