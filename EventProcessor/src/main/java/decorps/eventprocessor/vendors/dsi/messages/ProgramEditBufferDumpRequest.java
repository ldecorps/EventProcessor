package decorps.eventprocessor.vendors.dsi.messages;

import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.DSI_ID;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.End_Of_Exclusive;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.RequestProgramEditBufferTransmit;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.System_Exclusive;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Tetra_ID;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class ProgramEditBufferDumpRequest extends EventProcessorSysexMessage {

	public static final byte[] message = new byte[] { System_Exclusive, DSI_ID, Tetra_ID,
			RequestProgramEditBufferTransmit, End_Of_Exclusive };

	protected ProgramEditBufferDumpRequest() {
		super(message);
	}

}
