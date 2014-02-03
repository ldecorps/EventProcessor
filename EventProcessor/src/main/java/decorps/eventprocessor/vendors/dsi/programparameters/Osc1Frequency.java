package decorps.eventprocessor.vendors.dsi.programparameters;

public class Osc1Frequency extends ProgramParameter implements
		ZeroTo120Range {

	public Osc1Frequency(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 0;
	}

}
