package decorps.eventprocessor.vendors.dsi.programparameters;

public class GlideMode extends ProgramParameter implements ZeroTo3Range {

	public GlideMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 11;
	}

}
