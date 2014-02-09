package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO3Shape extends ProgramParameter implements ZeroTo4Range {

	public LFO3Shape(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 48;
	}

}
