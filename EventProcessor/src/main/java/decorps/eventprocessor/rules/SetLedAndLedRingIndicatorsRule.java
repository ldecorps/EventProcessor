package decorps.eventprocessor.rules;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.ProgramEditBufferDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.livid.LividCodev2Map;
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
		EventProcessorSysexMessage setAllLed = (EventProcessorSysexMessage) map
				.mapToSetAllLedIndicators(ProgramParameterData
						.build(programEditBufferDataDump.unpacked));
		EventProcessorSysexMessage setAllRing = EventProcessorSysexMessage
				.build(LividCodev2Map.buildSet_LED_Ring_indicators());
		return EventProcessorMidiMessageComposite.buildComposite(setAllLed,
				setAllRing);
	}
}
