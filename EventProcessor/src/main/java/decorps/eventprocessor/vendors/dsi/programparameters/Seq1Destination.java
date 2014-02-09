package decorps.eventprocessor.vendors.dsi.programparameters;

public class Seq1Destination extends ProgramParameter implements ZeroTo47Range {

	public Seq1Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 77;
	}

}
