package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod3Source extends ProgramParameter implements ZeroTo20Range {

	public Mod3Source(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 71;
	}

}
