package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Repeat extends ProgramParameter implements ZeroOrOneRange {

	public Envelope3Repeat(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 98;
	}

}
