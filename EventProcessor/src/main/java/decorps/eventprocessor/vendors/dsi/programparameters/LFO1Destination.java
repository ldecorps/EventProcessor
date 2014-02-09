package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO1Destination extends ProgramParameter implements ZeroTo43Range {

	public LFO1Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 40;
	}

}
