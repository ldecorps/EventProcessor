package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO2Amount extends ProgramParameter implements ZeroTo127Range {

	public LFO2Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 44;
	}

}
