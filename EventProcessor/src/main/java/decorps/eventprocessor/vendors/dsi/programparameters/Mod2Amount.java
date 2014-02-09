package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod2Amount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public Mod2Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 69;
	}

}
