package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvVelocity extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvVelocity(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 31;
	}

}
