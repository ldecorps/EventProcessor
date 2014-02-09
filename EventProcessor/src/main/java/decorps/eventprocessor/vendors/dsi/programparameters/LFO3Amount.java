package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO3Amount extends ProgramParameter implements ZeroTo127Range {

	public LFO3Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 49;
	}

}
