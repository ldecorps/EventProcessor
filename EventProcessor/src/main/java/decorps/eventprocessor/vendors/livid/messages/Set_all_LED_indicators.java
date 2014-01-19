package decorps.eventprocessor.vendors.livid.messages;

public class Set_all_LED_indicators extends AbstractLividCode2SysexMessage {

	public Set_all_LED_indicators(int[] data, int specificByte,
			int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
