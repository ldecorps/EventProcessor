package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.vendors.livid.LividCodev2Map.buildSet_all_LED_indicators;
import static org.junit.Assert.assertArrayEquals;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.utils.BaseUtils;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class LividCodev2MapTest {
	@Test
	public void createSetAllLedIndicators() throws Exception {
		assertArrayEquals(
				new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04, 0x04, 0x1,
						0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, (byte) 0xF7 },
				buildSet_all_LED_indicators(0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7,
						0x8));
		assertArrayEquals(new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04,
				0x04, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0xF7 },
				buildSet_all_LED_indicators());
	}

	@Test
	public void canBuildSetLedRingIndicatorsSysex() throws Exception {
		byte[] expected = new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04, 0x1f };
		expected = ArrayUtils.addAll(expected, new byte[64]);
		expected = ArrayUtils.add(expected, (byte) 0xF7);
		assertArrayEquals(expected,
				LividCodev2Map.buildSet_LED_Ring_indicators(new int[] { 0x00 }));
	}

	@Test
	public void canBuilRequest_all_LED_indicators() throws Exception {
		byte[] expected = new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04, 0x7,
				0x04, (byte) 0xf7 };
		assertArrayEquals(expected, LividMessageFactory
				.buildRequest_all_LED_indicators().getMessage());
	}

	@Test
	public void canLitAllLeds() throws Exception {
		EventProcessor envProc = new EventProcessor();
		for (int i = 0; i <= 0x7f; i++) {
			byte[] Set_all_LED_indicators = LividCodev2Map
					.buildSet_all_LED_indicators(i, i, i, i, i, i, i, 0);
			SysexMessage setAllLedIndicator = new SysexMessage(
					Set_all_LED_indicators, Set_all_LED_indicators.length);
			envProc.fromTetraToLivid.receiver.send(setAllLedIndicator, -1);
		}
	}

	public void sendSetAllLed(EventProcessor envProc, int i)
			throws InvalidMidiDataException {
		int[] payload = BaseUtils.bytesToInts(BaseUtils.hexaToBytes(BaseUtils
				.intToHexa(i)));
		byte[] Set_all_LED_indicators = LividCodev2Map
				.buildSet_all_LED_indicators(payload);
		SysexMessage setAllLedIndicator = new SysexMessage(
				Set_all_LED_indicators, Set_all_LED_indicators.length);
		envProc.fromTetraToLivid.receiver.send(setAllLedIndicator, -1);
	}
}
