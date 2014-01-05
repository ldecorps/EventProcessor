package decorps.eventprocessor.rules;

import static decorps.eventprocessor.dsi.DsiTetraMap.DSI_ID;
import static decorps.eventprocessor.dsi.DsiTetraMap.End_Of_Exclusive;
import static decorps.eventprocessor.dsi.DsiTetraMap.RequestProgramEditBufferTransmit;
import static decorps.eventprocessor.dsi.DsiTetraMap.System_Exclusive;
import static decorps.eventprocessor.dsi.DsiTetraMap.Tetra_ID;
import decorps.eventprocessor.EventProcessorMidiMessage;
import decorps.eventprocessor.EventProcessorSysexMessage;

public class ProgramEditBufferDumpRequest implements Rule, ToTetra,
		PauseBeforeSend {

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		EventProcessorMidiMessage result = EventProcessorSysexMessage.build(
				System_Exclusive, DSI_ID, Tetra_ID,
				RequestProgramEditBufferTransmit, End_Of_Exclusive);
		return result;
	}

	public static ProgramEditBufferDumpRequest build() {
		return new ProgramEditBufferDumpRequest();
	}
}
