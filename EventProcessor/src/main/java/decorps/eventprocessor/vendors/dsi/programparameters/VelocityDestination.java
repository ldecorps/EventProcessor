package decorps.eventprocessor.vendors.dsi.programparameters;

public class VelocityDestination extends ProgramParameter implements
		ZeroTo47Range {

	public VelocityDestination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 88;
	}

}
