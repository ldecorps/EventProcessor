package decorps.eventprocessor.vendors.livid.messages;

public class Set_encoder_values extends AbstractLividCode2SysexMessage {

	public Set_encoder_values(int[] data, int specificByte,
			int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
