package decorps.eventprocessor.vendors.dsi.programparameters;

public class BreathAmount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public BreathAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 85;
	}

}
