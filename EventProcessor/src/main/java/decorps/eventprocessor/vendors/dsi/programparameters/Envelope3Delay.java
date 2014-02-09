package decorps.eventprocessor.vendors.dsi.programparameters;

public class Envelope3Delay extends ProgramParameter implements ZeroTo127Range {

	public Envelope3Delay(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 60;
	}

}
