package decorps.eventprocessor.vendors.livid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankLayoutTest {
	BankLayout cut = new BankLayout();

	@Test
	public void setFirstColumnOff() throws Exception {
		cut.setFirstColumnOff();
		assertEquals(0x0000, cut.getButtonsAsByteArrays()[0] & 0x00001111);
	}
}
