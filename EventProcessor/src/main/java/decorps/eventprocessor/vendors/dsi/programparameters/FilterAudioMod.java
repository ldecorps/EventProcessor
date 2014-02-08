package decorps.eventprocessor.vendors.dsi.programparameters;


public class FilterAudioMod extends ProgramParameter implements ZeroTo127Range {

	public FilterAudioMod(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 18;
	}

}
