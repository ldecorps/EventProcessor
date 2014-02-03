package decorps.eventprocessor.vendors.dsi.programparameters;

public class OscHardSync extends ProgramParameter implements
		ZeroOrOneRange {

	public OscHardSync(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 10;
	}

}
