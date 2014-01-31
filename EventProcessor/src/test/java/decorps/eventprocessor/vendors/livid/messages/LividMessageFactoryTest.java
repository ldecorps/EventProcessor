package decorps.eventprocessor.vendors.livid.messages;

import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;
import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.utils.BaseUtils;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Glide;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Encoder;

public class LividMessageFactoryTest {
	@Test
	public void createSetAllLedIndicators() throws Exception {
		assertArrayEquals(
				new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04, 0x04, 0x1,
						0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, (byte) 0xF7 },
				LividMessageFactory.buildSet_all_LED_indicators(0x1, 0x2, 0x3,
						0x4, 0x5, 0x6, 0x7, 0x8).getMessage());
		assertArrayEquals(new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04,
				0x04, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0xF7 },
				LividMessageFactory.buildSet_all_LED_indicators().getMessage());
	}

	@Test
	public void canLitAllLeds() throws Exception {
		EventProcessor envProc = EventProcessor.build();
		for (int i = 0; i <= 0x7f; i++) {
			byte[] Set_all_LED_indicators = LividMessageFactory
					.buildSet_all_LED_indicators(i, i, i, i, i, i, i, 0)
					.getMessage();
			SysexMessage setAllLedIndicator = new SysexMessage(
					Set_all_LED_indicators, Set_all_LED_indicators.length);
			envProc.fromTetraToLivid.receiver.send(setAllLedIndicator, -1);
		}
	}

	public void sendSetAllLed(EventProcessor envProc, int i)
			throws InvalidMidiDataException {
		int[] payload = BaseUtils.bytesToInts(BaseUtils.hexaToBytes(BaseUtils
				.intToHexa(i)));
		byte[] Set_all_LED_indicators = LividMessageFactory
				.buildSet_all_LED_indicators(payload).getMessage();
		SysexMessage setAllLedIndicator = new SysexMessage(
				Set_all_LED_indicators, Set_all_LED_indicators.length);
		envProc.fromTetraToLivid.receiver.send(setAllLedIndicator, -1);
	}

	@Test
	public void shouldBuildMessageForSettingEncoderTypeAbsoluteOrRelative()
			throws Exception {
		final Encoder relativeParam = new Encoder();
		relativeParam.setProgramParameter(new Osc1FineFreq(0, (byte) 0));
		final Encoder absoluteParam = new Encoder();
		absoluteParam.setProgramParameter(new Oscillator1Glide(0, (byte) 0));
		BankLayout.CurrentBank.encoders[0] = relativeParam;
		BankLayout.CurrentBank.encoders[1] = relativeParam;
		BankLayout.CurrentBank.encoders[2] = absoluteParam;

		int[] encoderModes = BankLayout.CurrentBank.getEncoderModes();
		EventProcessorMidiMessage message = LividMessageFactory
				.buildMap_Encosion_Mode(encoderModes);

		assertEquals("F0 00 01 61 04 10 03 00 00 00 00 00 00 00 F7",
				bytesToHexa(message.getMessage()));

	}
}
