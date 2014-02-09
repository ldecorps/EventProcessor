package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Amount extends ProgramParameter implements
		ZeroTo254Range, Centered {

	public Envelope3Amount(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 58;
	}

}
