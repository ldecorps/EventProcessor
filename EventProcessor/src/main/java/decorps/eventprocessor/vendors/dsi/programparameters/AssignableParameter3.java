package decorps.eventprocessor.vendors.dsi.programparameters;

public class AssignableParameter3 extends ProgramParameter implements
		ZeroTo183Range {

	public AssignableParameter3(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 107;
	}

}
