package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO4Frequency extends ProgramParameter implements ZeroTo166Range {

	public LFO4Frequency(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 47;
	}

}
