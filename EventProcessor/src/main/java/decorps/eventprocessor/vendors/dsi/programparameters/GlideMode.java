package decorps.eventprocessor.vendors.dsi.programparameters;

public class GlideMode extends AbstractProgramParameter implements ZeroTo3Range {

	protected GlideMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 11;
	}

}
