package decorps.eventprocessor.vendors.dsi.programparameters;

public class Osc2Frequency extends AbstractProgramParameter implements
		ZeroTo120Range {

	public Osc2Frequency(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 5;
	}

}
