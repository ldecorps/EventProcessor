package decorps.eventprocessor.vendors.dsi.programparameters;


public class AssignableParameter1 extends ProgramParameter implements
		ZeroTo183Range {

	public AssignableParameter1(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 105;
	}

}
