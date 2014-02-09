package decorps.eventprocessor.vendors.dsi.programparameters;

public class ModWheelDest extends ProgramParameter implements ZeroTo47Range {

	public ModWheelDest(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 82;
	}

}
