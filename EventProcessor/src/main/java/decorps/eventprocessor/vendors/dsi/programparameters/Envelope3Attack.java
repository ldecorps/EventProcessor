package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Attack extends ProgramParameter implements ZeroTo127Range {

	public Envelope3Attack(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 61;
	}

}
