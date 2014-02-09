package decorps.eventprocessor.vendors.dsi.programparameters;

public class FeedbackGain extends ProgramParameter implements ZeroTo127Range {

	public FeedbackGain(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 110;
	}

}
