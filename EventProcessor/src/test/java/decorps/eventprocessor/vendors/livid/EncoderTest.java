package decorps.eventprocessor.vendors.livid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncoderTest {
	@Test
	public void nullProgramParameter_akaDefaultEncoder_isAbsolute()
			throws Exception {
		assertEquals(new Encoder().getMode(), Mode.Absolute);
	}

	@Test
	public void getCcNumberForEncoders() throws Exception {
		BankLayout.CurrentBank.initialiseEncoders();
		assertEncoderCc(0, 1);
		assertEncoderCc(1, 5);
		assertEncoderCc(2, 9);
		assertEncoderCc(3, 13);
		assertEncoderCc(8, 2);
		assertEncoderCc(9, 6);
		assertEncoderCc(10, 10);
		assertEncoderCc(16, 3);
		assertEncoderCc(17, 7);
		assertEncoderCc(26, 12);
	}

	private void assertEncoderCc(int encoder, int cc) {
		assertEquals("encoder " + encoder, cc,
				BankLayout.CurrentBank.encoders[encoder].getCCOrNoteNumber());
	}
}
