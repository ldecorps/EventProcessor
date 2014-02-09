package decorps.eventprocessor.vendors.dsi.programparameters;


public class VCAEnvAttack extends ProgramParameter implements ZeroTo127Range {

	public VCAEnvAttack(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 33;
	}
}
