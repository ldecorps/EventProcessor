package decorps.eventprocessor.vendors.livid.messages;

public class Set_LED_Ring_Style extends AbstractLividCode2SysexMessage {
	public static final int WALK = 0;
	public static final int FILL = 1;
	public static final int EQ = 2;
	public static final int SPREAD = 3;

	public Set_LED_Ring_Style(int[] payloadAsCcNumbers, int specificByte1,
			int specificByte2, int maximumLength) {
		super(payloadAsCcNumbers, specificByte1, specificByte2, maximumLength);
	}

}
