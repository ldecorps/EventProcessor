package decorps.eventprocessor.vendors.dsi.programparameters;

public class AssignableParameter2 extends ProgramParameter implements
		ZeroTo183Range {

	public AssignableParameter2(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 106;
	}

}
