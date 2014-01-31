package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvAmount extends AbstractProgramParameter implements
		ZeroTo254Range {

	public FilterEnvAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 20;
	}

}
