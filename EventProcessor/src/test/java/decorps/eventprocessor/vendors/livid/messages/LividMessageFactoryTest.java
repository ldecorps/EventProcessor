package decorps.eventprocessor.vendors.livid.messages;

import static org.junit.Assert.assertArrayEquals;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.utils.BaseUtils;

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
		EventProcessor envProc = new EventProcessor();
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
}
