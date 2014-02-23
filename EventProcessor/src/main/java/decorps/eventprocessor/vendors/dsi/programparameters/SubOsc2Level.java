package decorps.eventprocessor.vendors.dsi.programparameters;

public class SubOsc2Level extends ProgramParameter implements ZeroTo127Range,
		Power {

	public SubOsc2Level(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 115;
	}

}
