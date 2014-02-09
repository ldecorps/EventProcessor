package decorps.eventprocessor.vendors.dsi.programparameters;

public class ArpeggiatorMode extends ProgramParameter implements ZeroTo14Range {

	public ArpeggiatorMode(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 100;
	}

}
