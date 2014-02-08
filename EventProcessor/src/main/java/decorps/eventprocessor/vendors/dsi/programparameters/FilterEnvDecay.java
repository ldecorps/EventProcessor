package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvDecay extends ProgramParameter implements ZeroTo127Range {

	public FilterEnvDecay(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 24;
	}

}
