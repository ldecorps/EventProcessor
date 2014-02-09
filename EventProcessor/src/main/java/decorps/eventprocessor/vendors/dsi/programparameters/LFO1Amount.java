package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO1Amount extends ProgramParameter implements ZeroTo127Range {

	public LFO1Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 39;
	}

}
