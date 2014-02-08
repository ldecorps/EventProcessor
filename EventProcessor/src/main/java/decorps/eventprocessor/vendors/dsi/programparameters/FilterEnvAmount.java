package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvAmount extends ProgramParameter implements
		ZeroTo254Range, Centered {

	public FilterEnvAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 20;
	}

}
