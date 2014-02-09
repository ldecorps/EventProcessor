package decorps.eventprocessor.vendors.dsi.programparameters;

public class SubOsc1Level extends ProgramParameter implements ZeroTo127Range {

	public SubOsc1Level(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 114;
	}

}
