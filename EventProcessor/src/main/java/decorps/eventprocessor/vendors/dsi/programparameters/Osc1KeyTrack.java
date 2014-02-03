package decorps.eventprocessor.vendors.dsi.programparameters;

public class Osc1KeyTrack extends ProgramParameter implements
		ZeroOrOneRange {

	public Osc1KeyTrack(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 4;
	}

}
