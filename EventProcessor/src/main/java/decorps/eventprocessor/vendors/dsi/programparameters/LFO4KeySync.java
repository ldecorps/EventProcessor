package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO4KeySync extends ProgramParameter implements ZeroOrOneRange {

	public LFO4KeySync(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 56;
	}

}
