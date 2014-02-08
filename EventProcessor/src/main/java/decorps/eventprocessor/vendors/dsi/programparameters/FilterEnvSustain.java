package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvSustain extends ProgramParameter implements
		ZeroTo127Range {

	public FilterEnvSustain(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 25;
	}

}
