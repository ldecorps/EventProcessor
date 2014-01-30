package decorps.eventprocessor.vendors.livid.messages;

public class Set_LED_Ring_Style extends AbstractLividCode2SysexMessage {
	public static final int WALK = 0;
	public static final int FILL = 1;
	public static final int EQ = 2;
	public static final int SPREAD = 3;

	public Set_LED_Ring_Style(int[] data, int specificByte, int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
