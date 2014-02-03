package decorps.eventprocessor.vendors.dsi.programparameters;

public class Oscillator2Shape extends ProgramParameter implements
		FourTo103Range, Centered {

	public Oscillator2Shape(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 7;
	}

}
