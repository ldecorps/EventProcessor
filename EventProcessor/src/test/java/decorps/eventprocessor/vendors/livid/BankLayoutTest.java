package decorps.eventprocessor.vendors.livid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankLayoutTest {
	BankLayout cut = new BankLayout();

	@Test
	public void setFirstColumnOff() throws Exception {
		cut.switchFirstColumnOff();
		assertEquals(0x0000, cut.getButtonsAsByteArrays()[0] & 0x00001111);
	}

	@Test
	public void lividShouldNotifyWhenItChangesBank() throws Exception {
		// Livid does not send messages when it changes bank...
		// all banks have to be loaded in advance, via change program, when new
		// patch is selected
	}
}
