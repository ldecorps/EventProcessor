package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod4Amount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public Mod4Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 75;
	}

}
