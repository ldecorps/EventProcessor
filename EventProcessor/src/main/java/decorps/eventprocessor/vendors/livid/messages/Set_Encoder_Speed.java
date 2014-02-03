package decorps.eventprocessor.vendors.livid.messages;

public class Set_Encoder_Speed extends AbstractLividCode2SysexMessage {

	public Set_Encoder_Speed(int[] data, int specificByte, int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
