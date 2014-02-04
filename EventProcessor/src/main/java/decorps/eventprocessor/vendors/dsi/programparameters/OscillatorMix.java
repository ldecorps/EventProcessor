package decorps.eventprocessor.vendors.dsi.programparameters;

public class OscillatorMix extends ProgramParameter implements ZeroTo127Range,
		Centered {

	public OscillatorMix(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 13;
	}

}
