package decorps.eventprocessor.vendors.dsi.programparameters;

public class ProgramVolume extends ProgramParameter implements ZeroTo127Range,
		Power {

	public ProgramVolume(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 29;
	}

}
