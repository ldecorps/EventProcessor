package decorps.eventprocessor.vendors.dsi.messages;

import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;

public class EventProcessorNRPNMessageTest {
	public static byte[] sampleReceivedNPRN_Osc1_FineTune_3 = new byte[] {
			0x63, 0x00, 0x62, 0x01, 0x06, 0x00, 0x26, 0x32 };
	public static byte[] sampleReceivedNPRN_Osc1_Glide_1 = new byte[] { 0x63,
			0x00, 0x62, 0x03, 0x06, 0x00, 0x26, 0x01 };
	public static byte[] sampleReceivedFilterEnvelopeAmount_to_254 = new byte[] {
			0x63, 0x00, 0x62, 0x14, 0x06, 0x01, 0x26, 0x7E };
	public static final EventProcessorMidiMessage oneNRPNMessage = EventProcessorNRPNMessage
			.buildEventProcessorNRPNMessage(3, 1);

	@Test
	public void testSimpleCase() throws Exception {
		EventProcessorNRPNMessage oneMessage = (EventProcessorNRPNMessage) EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(3, 1);
		assertThat(oneMessage.eventProcessorMidiMessages, hasSize(4));
		assertArrayEquals(sampleReceivedNPRN_Osc1_Glide_1,
				oneMessage.getMessage());
	}

	@Test
	public void testRelativeCase() throws Exception {
		EventProcessorNRPNMessage oneMessage = (EventProcessorNRPNMessage) EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(new Osc1FineFreq((byte) 50));
		assertArrayEquals(bytesToHexa(oneMessage.getMessage()),
				sampleReceivedNPRN_Osc1_FineTune_3, oneMessage.getMessage());
	}

	@Test
	public void testTwoBytesParameterValue() throws Exception {
		EventProcessorNRPNMessage oneMessage = (EventProcessorNRPNMessage) EventProcessorNRPNMessage
				.buildEventProcessorNRPNMessage(new FilterEnvAmount(25,
						(byte) 254));
		assertArrayEquals(sampleReceivedFilterEnvelopeAmount_to_254,
				oneMessage.getMessage());
	}
}
