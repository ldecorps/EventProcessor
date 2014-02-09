package decorps.eventprocessor.vendors.dsi.programparameters;

public class Mod3Amount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public Mod3Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 72;
	}

}
