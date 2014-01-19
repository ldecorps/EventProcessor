package decorps.eventprocessor.vendors.dsi.messages;

import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.End_Of_Exclusive;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Inquiry_Message;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Inquiry_Request;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Midi_Channel_all;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Non_realtime_message;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.System_Exclusive;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class Universal_System_Exclusive_Message_Device_Inquiry extends
		EventProcessorSysexMessage {

	public static final byte[] message = new byte[] { System_Exclusive, Non_realtime_message,
			Midi_Channel_all, Inquiry_Message, Inquiry_Request,
			End_Of_Exclusive };

	Universal_System_Exclusive_Message_Device_Inquiry() {
		super(message);
	}

}
