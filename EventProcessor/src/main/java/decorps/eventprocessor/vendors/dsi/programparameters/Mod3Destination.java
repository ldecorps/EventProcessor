package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod3Destination extends ProgramParameter implements ZeroTo47Range {

	public Mod3Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 73;
	}

}
