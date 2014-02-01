package decorps.eventprocessor.vendors.livid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncoderTest {
	@Test
	public void nullProgramParameter_akaDefaultEncoder_isAbsolute()
			throws Exception {
		assertEquals(new Encoder().getMode(), Mode.Absolute);
	}
}
