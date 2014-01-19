package decorps.eventprocessor.vendors.livid;

import static org.junit.Assert.assertArrayEquals;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class LividCodev2MapTest {

	@Test
	public void canBuildSetLedRingIndicatorsSysex() throws Exception {
		byte[] expected = new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04, 0x1f };
		expected = ArrayUtils.addAll(expected, new byte[64]);
		expected = ArrayUtils.add(expected, (byte) 0xF7);
		assertArrayEquals(expected, LividMessageFactory
				.buildSet_LED_Ring_indicators().getMessage());
	}

	@Test
	public void canBuilRequest_all_LED_indicators() throws Exception {
		byte[] expected = new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04, 0x7,
				0x04, (byte) 0xf7 };
		assertArrayEquals(expected, LividMessageFactory
				.buildRequest_all_LED_indicators().getMessage());
	}

}
