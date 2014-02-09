package decorps.eventprocessor.vendors.dsi.programparameters;

public class Seq2Destination extends ProgramParameter implements ZeroTo47Range {

	public Seq2Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 78;
	}

}
