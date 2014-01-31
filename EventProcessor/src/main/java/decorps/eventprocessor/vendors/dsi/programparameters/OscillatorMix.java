package decorps.eventprocessor.vendors.dsi.programparameters;

public class OscillatorMix extends AbstractProgramParameter implements
		ZeroTo127Range {

	public OscillatorMix(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 13;
	}

}
