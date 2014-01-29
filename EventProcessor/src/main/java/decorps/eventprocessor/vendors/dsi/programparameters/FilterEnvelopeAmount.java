package decorps.eventprocessor.vendors.dsi.programparameters;

public class FilterEnvelopeAmount extends AbstractProgramParameter implements
		ZeroTo254Range {

	public FilterEnvelopeAmount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getNRPNNumber() {
		return 20;
	}

}
