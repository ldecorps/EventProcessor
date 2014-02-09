package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Sustain extends ProgramParameter implements
		ZeroTo127Range {

	public Envelope3Sustain(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 63;
	}

}
