package decorps.eventprocessor.vendors.livid.messages;

public class Button_Toggle_Mode_Enable extends AbstractLividCode2SysexMessage {

	public Button_Toggle_Mode_Enable(int[] data, byte specificByte1,
			byte specificByte2, int maximumLength) {
		super(data, specificByte1, specificByte2, maximumLength);
	}

}
