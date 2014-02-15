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
		BankLayout.CurrentBank.initialiseControllers();
		assertEncoderCc(0, 1);
		assertEncoderCc(8, 2);
		assertEncoderCc(16, 3);
		assertEncoderCc(24, 4);
		assertEncoderCc(1, 5);
	}

	private void assertEncoderCc(int encoder, int cc) {
		assertEquals("encoder " + encoder, cc,
				BankLayout.CurrentBank.encoders[encoder].getCCOrNoteNumber());
	}

	@Test
	public void getEncoderIdForCc() throws Exception {
		BankLayout.CurrentBank.initialiseControllers();
		assertCcEncoder(1, 0);
		assertCcEncoder(2, 8);
		assertCcEncoder(3, 16);
		assertCcEncoder(4, 24);
		assertCcEncoder(5, 1);
		assertCcEncoder(6, 9);
		assertCcEncoder(7, 17);
		assertCcEncoder(8, 25);
		assertCcEncoder(9, 2);
		assertCcEncoder(10, 10);
		assertCcEncoder(11, 18);
		assertCcEncoder(12, 26);
	}

	private void assertCcEncoder(int cc, int encoder) {
		assertEquals("cc " + cc, encoder, Encoder.getIdForCc(cc));
	}
}
