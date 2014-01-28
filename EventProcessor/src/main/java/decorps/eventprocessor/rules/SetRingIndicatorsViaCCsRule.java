package decorps.eventprocessor.rules;

import static decorps.eventprocessor.utils.BaseUtils.printOutBytesAsHexa;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.ProgramEditBufferDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.maps.TetraProgramParameterToLividCodeV2;

public class SetRingIndicatorsViaCCsRule implements Rule {
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
		System.out.println(programEditBufferDataDump);
		printOutBytesAsHexa(upacked.data);
		EventProcessorMidiMessage setAllRingViaCcs = map.mapToCcs(upacked);
		synchronized (this.getClass()) {
			SetRingIndicatorsViaCCsRule.class.notify();
		}
		return setAllRingViaCcs;
	}
}
