package decorps.eventprocessor.vendors.dsi.programparameters;

public class UnissonOnOff extends ProgramParameter implements ZeroOrOneRange {

	public UnissonOnOff(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 99;
	}

}
