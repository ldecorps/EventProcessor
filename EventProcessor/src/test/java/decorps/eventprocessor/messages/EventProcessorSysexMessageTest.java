package decorps.eventprocessor.messages;

import javax.sound.midi.SysexMessage;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class EventProcessorSysexMessageTest {
	@Test
	public void canSendOneSysEx() throws Exception {
		EventProcessor eventProcessor = EventProcessor.build();
		SysexMessage sysexMessage = new SysexMessage(
				SysexMessage.SYSTEM_EXCLUSIVE,
				DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry,
				DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry.length);
		eventProcessor.fromTetraToTetra.receiver.send(sysexMessage, -1);
	}
}
