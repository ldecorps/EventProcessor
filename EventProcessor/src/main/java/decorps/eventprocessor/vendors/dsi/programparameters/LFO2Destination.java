package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO2Destination extends ProgramParameter implements ZeroTo43Range {

	public LFO2Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 45;
	}

}
