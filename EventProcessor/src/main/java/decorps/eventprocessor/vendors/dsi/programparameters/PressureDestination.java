package decorps.eventprocessor.vendors.dsi.programparameters;

public class PressureDestination extends ProgramParameter implements
		ZeroTo47Range {

	public PressureDestination(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 84;
	}

}
