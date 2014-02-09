package decorps.eventprocessor.vendors.dsi.programparameters;

public class PushItMode extends ProgramParameter implements ZeroOrOneRange {

	public PushItMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 111;
	}

}
