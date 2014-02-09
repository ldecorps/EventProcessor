package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Decay extends ProgramParameter implements ZeroTo127Range {

	public Envelope3Decay(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 62;
	}

}
