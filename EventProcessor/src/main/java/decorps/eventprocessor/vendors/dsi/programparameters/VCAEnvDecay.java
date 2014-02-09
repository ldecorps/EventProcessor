package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvDecay extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvDecay(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 34;
	}

}
