package decorps.eventprocessor.vendors.dsi.programparameters;


public class Mod1Source extends ProgramParameter implements ZeroTo20Range {

	public Mod1Source(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 65;
	}

}
