package decorps.eventprocessor.vendors.dsi.programparameters;

public class Oscillator1Glide extends ProgramParameter implements
		ZeroTo127Range {

	public Oscillator1Glide(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 3;
	}

}
