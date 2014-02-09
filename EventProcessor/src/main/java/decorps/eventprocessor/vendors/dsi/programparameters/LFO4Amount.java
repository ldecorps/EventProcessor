package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO4Amount extends ProgramParameter implements ZeroTo127Range {

	public LFO4Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 54;
	}

}
