package decorps.eventprocessor.rules;

import static decorps.eventprocessor.utils.BaseUtils.printOutBytesAsHexa;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.ProgramEditBufferDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.maps.TetraProgramParameterToLividCodeV2;

public class SetLedAndLedRingIndicatorsRule implements Rule {
	static final TetraProgramParameterToLividCodeV2 map = new TetraProgramParameterToLividCodeV2();

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		final EventProcessorSysexMessage sysexMessage = eventProvessorMidiMessage
				.getAsSysexMessage();
		final byte[] message = sysexMessage.getMessage();
		final ProgramEditBufferDataDump programEditBufferDataDump = ProgramEditBufferDataDump
				.buildProgramEditBufferDataDump(message);
		final ProgramParameterData upacked = ProgramParameterData
				.build(programEditBufferDataDump.unpacked);
		printOutBytesAsHexa(upacked.data);
		EventProcessorSysexMessage setAllLed = (EventProcessorSysexMessage) map
				.mapToSetAllLedIndicators(upacked);
		EventProcessorSysexMessage setAllRing = (EventProcessorSysexMessage) map
				.mapToSetLedRingsIndicators(upacked);
		final EventProcessorMidiMessage composite = EventProcessorMidiMessageComposite
				.buildComposite(setAllLed, setAllRing);
		synchronized (this.getClass()) {
			SetLedAndLedRingIndicatorsRule.class.notify();
		}
		return composite;
	}
}
