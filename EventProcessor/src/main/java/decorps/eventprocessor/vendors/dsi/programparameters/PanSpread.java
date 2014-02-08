package decorps.eventprocessor.vendors.dsi.programparameters;

public class PanSpread extends ProgramParameter implements ZeroTo127Range {

	public PanSpread(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 28;
	}

}
