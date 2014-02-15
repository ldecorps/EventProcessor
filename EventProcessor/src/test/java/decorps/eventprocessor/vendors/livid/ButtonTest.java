package decorps.eventprocessor.vendors.livid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ButtonTest {
	@Test
	public void getCcNumberForButton() throws Exception {
		BankLayout.CurrentBank.initialiseControllers();
		assertButtonCc(0, 1);
		assertButtonCc(1, 5);
		assertButtonCc(2, 9);
		assertButtonCc(3, 13);
		assertButtonCc(8, 2);
		assertButtonCc(9, 6);
		assertButtonCc(10, 10);
		assertButtonCc(16, 3);
		assertButtonCc(17, 7);
		assertButtonCc(26, 12);
	}

	private void assertButtonCc(int button, int cc) {
		assertEquals("button " + button, cc,
				BankLayout.CurrentBank.buttons[button].getCCOrNoteNumber());
	}

	@Test
	public void getButtonIdForCc() throws Exception {
		BankLayout.CurrentBank.initialiseControllers();
		assertCcButtonId(1, 0);
		assertCcButtonId(5, 1);
		assertCcButtonId(9, 2);
		assertCcButtonId(13, 3);
		assertCcButtonId(2, 8);
		assertCcButtonId(6, 9);
		assertCcButtonId(10, 10);
		assertCcButtonId(3, 16);
		assertCcButtonId(7, 17);
		assertCcButtonId(12, 26);
	}

	private void assertCcButtonId(int cc, int buttonId) {
		assertEquals("cc " + cc, buttonId, Button.getIdForCc(cc));
	}
}
