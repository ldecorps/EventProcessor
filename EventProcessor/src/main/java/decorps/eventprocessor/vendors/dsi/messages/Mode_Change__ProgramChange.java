package decorps.eventprocessor.vendors.dsi.messages;

import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.DSI_ID;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.End_Of_Exclusive;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Select_Program_Mode;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.System_Exclusive;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.Tetra_ID;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class Mode_Change__ProgramChange extends EventProcessorSysexMessage {

	public static final byte[] message = new byte[] { System_Exclusive, DSI_ID,
			Tetra_ID, Select_Program_Mode, End_Of_Exclusive };

	protected Mode_Change__ProgramChange() {
		super(message);
	}

}
