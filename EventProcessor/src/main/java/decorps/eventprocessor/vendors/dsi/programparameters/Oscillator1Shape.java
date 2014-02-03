package decorps.eventprocessor.vendors.dsi.programparameters;

public class Oscillator1Shape extends ProgramParameter implements
		FourTo103Range, Centered {

	public Oscillator1Shape(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 2;
	}

}
