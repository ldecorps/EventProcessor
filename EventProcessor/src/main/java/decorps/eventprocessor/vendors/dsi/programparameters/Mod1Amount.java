package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod1Amount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public Mod1Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 66;
	}

}
