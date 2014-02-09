package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO2Shape extends ProgramParameter implements ZeroTo4Range {

	public LFO2Shape(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 43;
	}

}
