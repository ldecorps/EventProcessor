package decorps.eventprocessor.vendors.dsi.programparameters;

public class SequenceurOnOff extends ProgramParameter implements
		ZeroOrOneRange {

	public SequenceurOnOff(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 101;
	}

}
