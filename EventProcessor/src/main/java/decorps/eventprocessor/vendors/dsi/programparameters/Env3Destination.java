package decorps.eventprocessor.vendors.dsi.programparameters;


public class Env3Destination extends ProgramParameter implements ZeroTo43Range {

	public Env3Destination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 57;
	}

}
