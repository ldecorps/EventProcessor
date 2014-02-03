package decorps.eventprocessor.vendors.dsi.programparameters;

public class OscillatorSlop extends ProgramParameter implements ZeroTo5Range {

	public OscillatorSlop(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 12;
	}

}
