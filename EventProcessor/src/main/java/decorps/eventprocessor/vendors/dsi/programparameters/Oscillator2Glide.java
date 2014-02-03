package decorps.eventprocessor.vendors.dsi.programparameters;

public class Oscillator2Glide extends ProgramParameter implements
		ZeroTo127Range {

	public Oscillator2Glide(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 8;
	}

}
