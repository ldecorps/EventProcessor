package decorps.eventprocessor.vendors.dsi.programparameters;

public class LFO2Frequency extends ProgramParameter implements ZeroTo166Range {

	public LFO2Frequency(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 42;
	}

}
