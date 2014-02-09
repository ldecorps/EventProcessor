package decorps.eventprocessor.vendors.dsi.programparameters;

public class UnissonMode extends ProgramParameter implements ZeroTo4Range {

	public UnissonMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 96;
	}

}
