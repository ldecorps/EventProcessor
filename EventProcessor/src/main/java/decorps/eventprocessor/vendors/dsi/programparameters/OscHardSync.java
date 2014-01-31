package decorps.eventprocessor.vendors.dsi.programparameters;

public class OscHardSync extends AbstractProgramParameter implements
		ZeroOrOneRange {

	public OscHardSync(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 10;
	}

}
