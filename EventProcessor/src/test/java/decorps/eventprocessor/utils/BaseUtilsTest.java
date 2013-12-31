package decorps.eventprocessor.utils;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaseUtilsTest {
	@Test
	public void canConvertFromByteToInt() throws Exception {
		checkByteToInt("0", 0);
		checkByteToInt("1", 1);
		checkByteToInt("10", 2);
	}

	void checkByteToInt(String string, int expectedInt) {
		assertEquals(expectedInt, binaryToByte(string));
	}

	@Test
	public void canGrabMsb() throws Exception {
		byte eightBits = binaryToByte("1100 0000");

		assertEquals(BaseUtils.getMsb(eightBits), binaryToByte("1100"));
	}
}
