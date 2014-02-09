package decorps.eventprocessor.vendors.dsi.programparameters;

public class PressureAmount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public PressureAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 83;
	}

}
