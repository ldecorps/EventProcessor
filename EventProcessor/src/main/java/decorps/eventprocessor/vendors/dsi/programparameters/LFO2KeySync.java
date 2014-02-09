package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO2KeySync extends ProgramParameter implements ZeroOrOneRange {

	public LFO2KeySync(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 46;
	}

}
