package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO1Shape extends ProgramParameter implements ZeroTo4Range {

	public LFO1Shape(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 38;
	}

}
