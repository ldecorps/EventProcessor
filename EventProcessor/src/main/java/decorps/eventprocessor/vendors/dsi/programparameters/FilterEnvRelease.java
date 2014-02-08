package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvRelease extends ProgramParameter implements
		ZeroTo127Range {

	public FilterEnvRelease(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 26;
	}

}
