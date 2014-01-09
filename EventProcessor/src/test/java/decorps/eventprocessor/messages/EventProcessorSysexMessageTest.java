package decorps.eventprocessor.messages;

import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.sound.midi.SysexMessage;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.RulesAwareReceiverWrapper;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class EventProcessorSysexMessageTest {
	@Test
	public void canSendOneSysEx() throws Exception {
		EventProcessor eventProcessor = EventProcessor.build();
		SysexMessage sysexMessage = new SysexMessage();
		sysexMessage
				.setMessage(
						DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry,
						DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry.length);
		eventProcessor.fromLocalToLocal.receiver.send(sysexMessage, -1);
		synchronized (RulesAwareReceiverWrapper.wait) {
			if (eventProcessor.fromLocalToLocal.receiver.getSentMidiMessages()
					.isEmpty())
				RulesAwareReceiverWrapper.wait.wait();
		}
		EventProcessorMidiMessage sentSysex = eventProcessor.fromLocalToLocal.receiver
				.getSentMidiMessages().get(0).getAsSysexMessage();
		assertThat(
				"sending sysex "
						+ bytesToHexa(DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry),
				bytesToHexa(sentSysex.getMessage()),
				is(bytesToHexa(sysexMessage.getMessage())));
	}

}
