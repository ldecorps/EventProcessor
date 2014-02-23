package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterCutoffFreq extends ProgramParameter implements
		ZeroTo164Range, Power {

	public FilterCutoffFreq(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 15;
	}

}
