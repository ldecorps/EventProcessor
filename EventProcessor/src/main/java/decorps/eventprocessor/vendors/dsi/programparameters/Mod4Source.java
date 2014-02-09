package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod4Source extends ProgramParameter implements ZeroTo20Range {

	public Mod4Source(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 74;
	}

}
