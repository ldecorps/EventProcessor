package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.vendors.livid.LividCodev2Map.SetAllLedIndicators;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class LividCodev2MapTest {
	@Test
	public void createSetAllLedIndicators() throws Exception {
		assertArrayEquals(new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04,
				0x04, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, (byte) 0xF7 },
				SetAllLedIndicators(0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8));
		assertArrayEquals(new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61, 0x04,
				0x04, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0xF7 },
				SetAllLedIndicators());
	}
}
