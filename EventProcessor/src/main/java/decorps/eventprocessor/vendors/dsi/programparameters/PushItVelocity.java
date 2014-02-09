package decorps.eventprocessor.vendors.dsi.programparameters;

public class PushItVelocity extends ProgramParameter implements ZeroTo127Range {

	public PushItVelocity(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 111;
	}

}
