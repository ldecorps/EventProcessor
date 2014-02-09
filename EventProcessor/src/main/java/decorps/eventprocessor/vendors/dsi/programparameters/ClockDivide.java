package decorps.eventprocessor.vendors.dsi.programparameters;

public class ClockDivide extends ProgramParameter implements ZeroTo20Range,
		ZeroTo12Range {

	public ClockDivide(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 92;
	}

}
