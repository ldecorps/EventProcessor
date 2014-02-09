package decorps.eventprocessor.vendors.dsi.programparameters;

public class PushItNote extends ProgramParameter implements ZeroTo127Range {

	public PushItNote(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 112;
	}

}
