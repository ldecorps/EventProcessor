package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterResonance extends ProgramParameter implements
		ZeroTo127Range {

	protected FilterResonance(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 16;
	}

}
