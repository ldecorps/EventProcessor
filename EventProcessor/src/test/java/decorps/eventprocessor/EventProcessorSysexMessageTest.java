package decorps.eventprocessor;

import javax.sound.midi.SysexMessage;

import org.junit.Test;

import decorps.eventprocessor.dsi.DsiTetraMap;

public class EventProcessorSysexMessageTest {
	@Test
	public void canSendOneSysEx() throws Exception {
		EventProcessor eventProcessor = new EventProcessor();
		SysexMessage sysexMessage = new SysexMessage(
				DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry,
				DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry.length);
		eventProcessor.fromTetraToTetra.receiver.send(sysexMessage, -1);
	}
}
