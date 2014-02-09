package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvDelay extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvDelay(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 32;
	}

}
