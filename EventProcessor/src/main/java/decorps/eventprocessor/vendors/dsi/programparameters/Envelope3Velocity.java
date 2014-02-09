package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Velocity extends ProgramParameter implements
		ZeroTo127Range {

	public Envelope3Velocity(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 59;
	}

}
