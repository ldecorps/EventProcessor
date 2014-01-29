package decorps.eventprocessor.vendors.dsi.programparameters;

public class Resonance extends AbstractProgramParameter implements
		ZeroTo127Range {

	protected Resonance(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getNRPNNumber() {
		return 16;
	}

}
