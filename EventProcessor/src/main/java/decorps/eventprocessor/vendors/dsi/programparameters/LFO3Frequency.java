package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO3Frequency extends ProgramParameter implements ZeroTo166Range {

	public LFO3Frequency(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 47;
	}

}
