package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterKeyboardAmt extends ProgramParameter implements
		ZeroTo127Range {

	public FilterKeyboardAmt(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 17;
	}

}
