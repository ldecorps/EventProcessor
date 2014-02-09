package decorps.eventprocessor.vendors.dsi.programparameters;

public class Seq4Destination extends ProgramParameter implements ZeroTo47Range {

	public Seq4Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 80;
	}

}
