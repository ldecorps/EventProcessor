package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Release extends ProgramParameter implements
		ZeroTo127Range {

	public Envelope3Release(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 64;
	}

}
