package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCALevel extends ProgramParameter implements ZeroTo127Range {

	public VCALevel(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 27;
	}

}
