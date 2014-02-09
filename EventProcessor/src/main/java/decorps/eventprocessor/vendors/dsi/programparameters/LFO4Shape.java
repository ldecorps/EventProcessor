package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO4Shape extends ProgramParameter implements ZeroTo4Range {

	public LFO4Shape(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 53;
	}

}
