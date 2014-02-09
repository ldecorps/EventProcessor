package decorps.eventprocessor.vendors.dsi.programparameters;

public class FootControlAmt extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public FootControlAmt(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 89;
	}

}
