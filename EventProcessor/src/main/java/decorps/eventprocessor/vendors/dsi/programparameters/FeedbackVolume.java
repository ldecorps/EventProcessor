package decorps.eventprocessor.vendors.dsi.programparameters;

public class FeedbackVolume extends ProgramParameter implements ZeroTo127Range,
		Power {

	public FeedbackVolume(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 116;
	}

}
