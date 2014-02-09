package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvRelease extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvRelease(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 36;
	}

}
