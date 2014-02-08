package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvDelay extends ProgramParameter implements ZeroTo127Range {

	public FilterEnvDelay(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 22;
	}

}
