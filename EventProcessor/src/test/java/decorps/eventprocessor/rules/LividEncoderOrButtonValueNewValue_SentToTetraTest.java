package decorps.eventprocessor.rules;

import static decorps.eventprocessor.utils.BaseUtils.decodeMessage;
import static org.junit.Assert.assertEquals;

import javax.sound.midi.ShortMessage;

import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class LividEncoderOrButtonValueNewValue_SentToTetraTest {

	Rule cut = new LividEncoderOrButtonValueNewValue_SentToTetra();

	@Test
	public void buttonPressedSendNewValue() throws Exception {
		BankLayout.CurrentBank.setButtonsOn(1, 2);

		final EventProcessorMidiMessage switchOscillator1_Off = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.NOTE_OFF, 0, 33, 0);
		System.out.println(decodeMessage(switchOscillator1_Off));

		EventProcessorNRPNMessage result = cut.transform(switchOscillator1_Off)
				.getAsEventProcessorNRPNMessage();

		assertEquals(2, result.NRPNControllerNumber);
		assertEquals(0, result.NRPNControllerValue);
	}
}
