package decorps.eventprocessor.vendors.dsi.programparameters;

public class BreathDestination extends ProgramParameter implements
		ZeroTo47Range {

	public BreathDestination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 86;
	}

}
