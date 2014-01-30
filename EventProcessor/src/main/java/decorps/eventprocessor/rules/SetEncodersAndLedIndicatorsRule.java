package decorps.eventprocessor.rules;

import static decorps.eventprocessor.utils.BaseUtils.printOutBytesAsHexa;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.ProgramEditBufferDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.maps.TetraProgramParameterToLividCodeV2;

public class SetEncodersAndLedIndicatorsRule implements Rule {
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
		EventProcessorSysexMessage setAllEncoderValues = (EventProcessorSysexMessage) map
				.mapToSetAllEncoderValues(upacked);
		final EventProcessorMidiMessage composite = EventProcessorMidiMessageComposite
				.buildComposite(setAllLed, setAllEncoderValues);
		synchronized (this.getClass()) {
			SetEncodersAndLedIndicatorsRule.class.notify();
		}
		return composite;
	}
}
