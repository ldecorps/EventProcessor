package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO3KeySync extends ProgramParameter implements ZeroOrOneRange {

	public LFO3KeySync(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 51;
	}

}
