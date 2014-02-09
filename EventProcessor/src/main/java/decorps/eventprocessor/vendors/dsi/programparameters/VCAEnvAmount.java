package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvAmount extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 30;
	}

}
