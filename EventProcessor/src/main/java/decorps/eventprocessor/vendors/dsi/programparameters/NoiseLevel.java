package decorps.eventprocessor.vendors.dsi.programparameters;

public class NoiseLevel extends ProgramParameter implements ZeroTo127Range {

	public NoiseLevel(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 14;
	}

}
