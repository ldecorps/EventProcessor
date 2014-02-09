package decorps.eventprocessor.vendors.dsi.programparameters;

public class ArpeggiatorOnOff extends ProgramParameter implements ZeroOrOneRange {

	public ArpeggiatorOnOff(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 99;
	}

}
