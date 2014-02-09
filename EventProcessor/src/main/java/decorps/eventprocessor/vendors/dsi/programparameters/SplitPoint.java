package decorps.eventprocessor.vendors.dsi.programparameters;

public class SplitPoint extends ProgramParameter implements ZeroTo127Range {

	public SplitPoint(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 118;
	}

}
