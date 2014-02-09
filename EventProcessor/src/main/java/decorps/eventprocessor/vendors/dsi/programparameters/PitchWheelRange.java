package decorps.eventprocessor.vendors.dsi.programparameters;

public class PitchWheelRange extends ProgramParameter implements ZeroTo12Range {

	public PitchWheelRange(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 93;
	}

}
