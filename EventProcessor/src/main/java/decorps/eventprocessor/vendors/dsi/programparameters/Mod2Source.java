package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod2Source extends ProgramParameter implements ZeroTo20Range {

	public Mod2Source(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 68;
	}

}
