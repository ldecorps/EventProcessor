package decorps.eventprocessor.vendors.dsi.programparameters;

public class SequencerTrigger extends ProgramParameter implements ZeroTo4Range {

	public SequencerTrigger(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 94;
	}

}
