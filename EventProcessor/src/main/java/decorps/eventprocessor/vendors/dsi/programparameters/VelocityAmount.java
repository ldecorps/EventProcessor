package decorps.eventprocessor.vendors.dsi.programparameters;

public class VelocityAmount extends ProgramParameter implements ZeroTo254Range,
		Centered {

	public VelocityAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 87;
	}

}
