package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvSustain extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvSustain(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 35;
	}

}
