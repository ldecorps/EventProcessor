package decorps.eventprocessor.rules;

import static decorps.eventprocessor.vendors.dsi.DsiTetraMap.DSI_ID;
import static decorps.eventprocessor.vendors.dsi.DsiTetraMap.RequestProgramEditBufferTransmit;
import static decorps.eventprocessor.vendors.dsi.DsiTetraMap.Tetra_ID;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class ProgramEditBufferDumpRequest implements Rule {

	public static final EventProcessorSysexMessage programEditBufferDumpRequest = EventProcessorSysexMessage.build(
			DsiTetraMap.System_Exclusive, DSI_ID, Tetra_ID,
			RequestProgramEditBufferTransmit, DsiTetraMap.End_Of_Exclusive);

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		EventProcessorMidiMessage result = programEditBufferDumpRequest;
		return result;
	}

	public static ProgramEditBufferDumpRequest build() {
		return new ProgramEditBufferDumpRequest();
	}
}
