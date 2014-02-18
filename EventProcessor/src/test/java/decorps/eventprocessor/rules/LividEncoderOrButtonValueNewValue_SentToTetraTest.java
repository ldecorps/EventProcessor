package decorps.eventprocessor.rules;

import static decorps.eventprocessor.utils.BaseUtils.decodeMessage;
import static org.junit.Assert.*;

import javax.sound.midi.ShortMessage;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.messages.EventProcessorNRPNMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Button;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.livid.Encoder;
import decorps.eventprocessor.vendors.maps.MapRepository;
import decorps.eventprocessor.vendors.maps.Oscillator1ShapeMap;

public class LividEncoderOrButtonValueNewValue_SentToTetraTest {

	Rule cut = new LividEncoderOrButtonValueNewValue_SentToTetra();

	@Before
	public void initialise() {
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;
		MapRepository.initialise();
	}

	@Test
	public void buttonPressed_SendsNewValue() throws Exception {
		new Oscillator1ShapeMap();
		BankLayout.CurrentBank.switchButtonsOn(1, 2);
		final EventProcessorMidiMessage switchOscillator1_Off = Button
				.switchButtonOff(BankLayout.CurrentBank.buttons[0]);
		System.out.println(decodeMessage(switchOscillator1_Off));

		EventProcessorMidiMessage tmp = cut.transform(switchOscillator1_Off);

		EventProcessorNRPNMessage result = tmp.getMessageFromComposite(0)
				.getAsEventProcessorNRPNMessage();

		assertEquals("NRPN number", 2, result.NRPNControllerNumber);
		assertEquals("NRPN value", 0, result.NRPNControllerValue);
	}

	@Test
	public void givenOsc1FineTuneIsAtZero_whenTurnedOneTick_SendsCorrectValueTsoTetra()
			throws Exception {
		Encoder oscFineTuneEncoder = ControllerRepository
				.getEncoderForParameterClass(Osc1FineFreq.class);
		assertNotNull(oscFineTuneEncoder);
		oscFineTuneEncoder.decrementUntil(0);
		final int lividCcOutputted = oscFineTuneEncoder.getCCOrNoteNumber();
		assertEquals(2, lividCcOutputted);
		final EventProcessorMidiMessage lividCc_turnOscFineTuneFromZeroTo1 = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE, 0,
						lividCcOutputted, 1);

		final EventProcessorMidiMessage transformedMessage = cut
				.transform(lividCc_turnOscFineTuneFromZeroTo1);
		EventProcessorNRPNMessage result = transformedMessage
				.getMessageFromComposite(0).getAsEventProcessorNRPNMessage();

		assertEquals("NRPN number", oscFineTuneEncoder.getProgramParameter()
				.getLayerANRPNNumber(), result.NRPNControllerNumber);
		assertEquals("NRPN value", 1, result.NRPNControllerValue);
	}

}
