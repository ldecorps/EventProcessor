package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO3Destination extends ProgramParameter implements ZeroTo43Range {

	public LFO3Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 60;
	}

}
