package decorps.eventprocessor.vendors.livid;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankLayoutTest {
	BankLayout cut = new BankLayout(1);

	@Test
	public void setFirstColumnOff() throws Exception {
		cut.switchFirstColumnOff();
		assertEquals(0x0000, cut.getButtonsAsByteArrays()[0] & 0x00001111);
	}

	@Test
	public void newBank_shouldHaveAllItsEncodersAssingToNullParameter()
			throws Exception {

		Encoder[] encoders = new BankLayout(1).encoders;
		for (Encoder encoder : encoders)
			assertEquals(Mode.Absolute, encoder.getMode());
		assertThat(encoders.length, is(32));
	}

	@Test
	public void encodersId_followTheLividDefault() throws Exception {
		BankLayout bankLayout = new BankLayout(1);
		for (int i = 0; i < 32; i++)
			assertEquals(i, bankLayout.encoders[i].id);
	}
}
