package decorps.eventprocessor.vendors.livid.messages;

public class Set_LED_Ring_indicators extends AbstractLividCode2SysexMessage {

	public Set_LED_Ring_indicators(int[] data, int specificByte,
			int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
