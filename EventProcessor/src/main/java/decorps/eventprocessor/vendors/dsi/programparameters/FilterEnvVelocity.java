package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvVelocity extends ProgramParameter implements
		ZeroTo127Range {

	public FilterEnvVelocity(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 21;
	}

}
