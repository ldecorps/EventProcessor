package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterConfigMode extends ProgramParameter implements
		ZeroOrOneRange {

	public FilterConfigMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 19;
	}

}
