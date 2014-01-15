package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.vendors.livid.LividCodev2Map.buildSet_all_LED_indicators;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import javax.sound.midi.SysexMessage;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import decorps.eventprocessor.EventProcessor;

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
		assertArrayEquals(expected,
				LividCodev2Map.buildRequest_all_LED_indicators());
	}

	@Test
	public void canLitAllLeds() throws Exception {
		byte[] Set_all_LED_indicators = LividCodev2Map
				.buildSet_all_LED_indicators(0x1f, 0x1f, 0x1f, 0x1f, 0x1f,
						0x1f, 0x1f);
		byte[] Request_all_LED_indicators = LividCodev2Map
				.buildRequest_all_LED_indicators();
		EventProcessor envProc = new EventProcessor();

		SysexMessage setAllLedIndicator = new SysexMessage(
				Set_all_LED_indicators, Set_all_LED_indicators.length);
		envProc.fromTetraToLivid.receiver.send(setAllLedIndicator, -1);
		SysexMessage requestAllLEDIndicators = new SysexMessage(
				Set_all_LED_indicators, Request_all_LED_indicators.length);
		envProc.fromTetraToLivid.receiver.send(requestAllLEDIndicators, -1);

		// check bytes 6, 7, 8, 9, 10, 11, 12 are set 1f too...
		fail("Read answer");
	}
}
