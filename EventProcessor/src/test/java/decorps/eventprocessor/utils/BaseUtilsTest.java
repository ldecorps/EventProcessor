package decorps.eventprocessor.utils;

import static decorps.eventprocessor.utils.BaseUtils.LINE_SEPARATOR;
import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static decorps.eventprocessor.utils.BaseUtils.hexaToBinary;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

	@Test
	public void hexa() throws Exception {
		assertThat(hexaToBinary("30", "F7"), is("0011 0000"
				+ BaseUtils.LINE_SEPARATOR + "1111 0111" + LINE_SEPARATOR));
	}

	@Test
	public void canConvertNonRealtimeMessage() throws Exception {
		checkByteToInt("0111 1110", 126);
	}
}
