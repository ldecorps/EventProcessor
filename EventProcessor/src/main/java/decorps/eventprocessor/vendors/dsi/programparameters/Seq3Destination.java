package decorps.eventprocessor.vendors.dsi.programparameters;

public class Seq3Destination extends ProgramParameter implements ZeroTo47Range {

	public Seq3Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 79;
	}

}
