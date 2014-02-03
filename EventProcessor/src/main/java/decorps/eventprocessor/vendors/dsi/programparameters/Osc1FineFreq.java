package decorps.eventprocessor.vendors.dsi.programparameters;

public class Osc1FineFreq extends ProgramParameter implements
		ZeroTo100Range, Centered {

	public Osc1FineFreq(int number, byte b) {
		super(number, b);
	}

	public Osc1FineFreq(byte b) {
		this(Integer.MAX_VALUE, b);
	}

	@Override
	public byte getLayerANRPNNumber() {
		return 1;
	}

}
