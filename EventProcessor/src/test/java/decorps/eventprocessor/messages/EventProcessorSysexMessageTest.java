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

	@Test
	public void unpackEightBytesPacketAsString() throws Exception {
		String[] packedPacket = new String[] { "0000 1100", "0000 0010",
				"0100 0000", "0111 0100", "0001 0110", "0000 0000",
				"0110 0100", "0011 0010" };
		String[] expectedUnpacked = new String[] { "0000 0010", "0100 0000",
				"1111 0100", "1001 0110", "0000 0000", "0110 0100", "0011 0010" };
		assertThat(expectedUnpacked,
				is(EventProcessorSysexMessage.unpack(packedPacket)));
	}

	@Test
	public void unpackEightBytesPackets() throws Exception {
		byte[] packedPacket = new byte[] { 0x0C, 0x02, 0x40, 0x74, 0x16, 0x00,
				0x64, 0x32 };
		byte[] expectedUnpacked = new byte[] { 0x02, 0x40, (byte) 0xF4,
				(byte) 0x96, 0x00, 0x64, 0x32 };
		assertThat(EventProcessorSysexMessage.unpack(packedPacket),
				is(expectedUnpacked));

	}
}
