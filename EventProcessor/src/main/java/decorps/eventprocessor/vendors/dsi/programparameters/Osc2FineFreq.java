package decorps.eventprocessor.vendors.dsi.programparameters;

public class Osc2FineFreq extends ProgramParameter implements
		ZeroTo100Range, Centered {

	public Osc2FineFreq(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 6;
	}

}
