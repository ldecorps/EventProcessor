package decorps.eventprocessor.vendors.livid.messages;

public class Set_Encoder_Values extends AbstractLividCode2SysexMessage {

	public Set_Encoder_Values(int[] data, int specificByte,
			int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
