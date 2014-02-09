package decorps.eventprocessor.vendors.dsi.programparameters;

public class ModWheelAmount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public ModWheelAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 81;
	}

}
