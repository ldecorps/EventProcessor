package decorps.eventprocessor.rules;

import static decorps.eventprocessor.utils.BaseUtils.printOutBytesAsHexa;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.vendors.dsi.ProgramEditBufferDataDump;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;
import decorps.eventprocessor.vendors.maps.TetraProgramParameterToLividCodeV2;

public class SetEncodersAndLedIndicatorsRule implements Rule {
	static final TetraProgramParameterToLividCodeV2 map = new TetraProgramParameterToLividCodeV2();

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		final EventProcessorSysexMessage sysexMessage = eventProvessorMidiMessage
				.getAsSysexMessage();
		final byte[] message = sysexMessage.getMessage();
		final ProgramEditBufferDataDump programEditBufferDataDump = ProgramEditBufferDataDump
				.buildProgramEditBufferDataDump(message);
		final ProgramParameterData programParameterData = ProgramParameterData
				.build(programEditBufferDataDump.unpacked);
		printOutBytesAsHexa(programParameterData.data);

		final EventProcessorMidiMessage composite = buildMidiMessagesForProgramParameterData(programParameterData);
		synchronized (SetEncodersAndLedIndicatorsRule.class) {
			SetEncodersAndLedIndicatorsRule.class.notifyAll();
		}
		return composite;
	}

	public EventProcessorMidiMessage buildMidiMessagesForProgramParameterData(
			final ProgramParameterData programParameterData) {

		final int[] buttonsAsArrayOfInts = BankLayout.CurrentBank
				.getButtonsAsArrayOfInts();
		byte[] Set_all_LED_indicators = LividMessageFactory
				.buildSet_all_LED_indicators(buttonsAsArrayOfInts).getMessage();
		EventProcessorMidiMessage setAllLed = EventProcessorMidiMessage
				.build(Set_all_LED_indicators);

		final int[] encoderValues = BankLayout.CurrentBank.getEncoderValues();
		EventProcessorMidiMessage setAllEncoderValues = LividMessageFactory
				.buildSet_Encoder_Values(encoderValues);

		final EventProcessorMidiMessage composite = EventProcessorMidiMessageComposite
				.buildComposite(setAllLed, setAllEncoderValues);
		return composite;
	}
}
