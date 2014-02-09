package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO1KeySync extends ProgramParameter implements ZeroOrOneRange {

	public LFO1KeySync(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 41;
	}

}
