package decorps.eventprocessor.vendors.dsi.programparameters;

public class Osc2KeyTrack extends AbstractProgramParameter implements
		ZeroOrOneRange {

	public Osc2KeyTrack(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 9;
	}

}
