package decorps.eventprocessor.vendors.livid.messages;

public class Request_all_LED_indicators extends AbstractLividCode2SysexMessage {

	public Request_all_LED_indicators() {
		super(new int[] { 0x04 }, 0x07, 1);
	}

}
