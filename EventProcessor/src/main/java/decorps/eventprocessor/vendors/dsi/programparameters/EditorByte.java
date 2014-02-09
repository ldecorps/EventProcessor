package decorps.eventprocessor.vendors.dsi.programparameters;

public class EditorByte extends ProgramParameter {

	public EditorByte(int number, byte b) {
		super(number, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 117;
	}

}
