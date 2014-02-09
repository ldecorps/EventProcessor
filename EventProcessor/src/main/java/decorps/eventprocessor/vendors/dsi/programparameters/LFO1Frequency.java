package decorps.eventprocessor.vendors.dsi.programparameters;


public class LFO1Frequency extends ProgramParameter implements ZeroTo166Range {

	public LFO1Frequency(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 37;
	}

}
