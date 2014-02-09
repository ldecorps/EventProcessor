package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO4Destination extends ProgramParameter implements ZeroTo43Range {

	public LFO4Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 50;
	}

}
