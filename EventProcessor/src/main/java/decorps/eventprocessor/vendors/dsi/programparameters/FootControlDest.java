package decorps.eventprocessor.vendors.dsi.programparameters;

public class FootControlDest extends ProgramParameter implements ZeroTo47Range {

	public FootControlDest(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 90;
	}

}
