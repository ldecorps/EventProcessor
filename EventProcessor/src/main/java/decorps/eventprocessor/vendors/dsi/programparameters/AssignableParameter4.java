package decorps.eventprocessor.vendors.dsi.programparameters;

public class AssignableParameter4 extends ProgramParameter implements
		ZeroTo183Range {

	public AssignableParameter4(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 108;
	}

}
