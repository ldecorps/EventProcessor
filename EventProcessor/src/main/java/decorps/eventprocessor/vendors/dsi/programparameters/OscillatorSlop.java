package decorps.eventprocessor.vendors.dsi.programparameters;

public class OscillatorSlop extends AbstractProgramParameter implements
		ZeroTo5Range {

	protected OscillatorSlop(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 12;
	}

}
