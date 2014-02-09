package decorps.eventprocessor.vendors.dsi.programparameters;


public class ClockBPM extends ProgramParameter implements ThirtyTo250Range {

	public ClockBPM(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 91;
	}

}
