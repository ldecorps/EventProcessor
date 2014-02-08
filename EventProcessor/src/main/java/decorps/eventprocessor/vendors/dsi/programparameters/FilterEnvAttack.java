package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvAttack extends ProgramParameter implements ZeroTo127Range {

	public FilterEnvAttack(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 23;
	}

}
