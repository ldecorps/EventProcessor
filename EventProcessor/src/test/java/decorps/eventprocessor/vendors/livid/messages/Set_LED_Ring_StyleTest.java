package decorps.eventprocessor.vendors.livid.messages;

import static decorps.eventprocessor.vendors.livid.BankLayout.CurrentBank;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class Set_LED_Ring_StyleTest {

	@Before
	public void initialise() {
		System.out.println("starting Set_LED_Ring_StyleTest before");
		// BankLayout.programParameterData =
		// ProgramParameterDataTest.sampleProgramParameterData;
		// BankLayout.createFourBanks();
		// MapRepository.initialise();
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;
		System.out.println("done with Set_LED_Ring_StyleTest before");
	}

	@Test
	public void buildsForCc() throws Exception {

		int encoder00Style = CurrentBank.encoders[0].getEncoderStyle();
		int encoder08Style = CurrentBank.encoders[8].getEncoderStyle();
		int encoder16Style = CurrentBank.encoders[16].getEncoderStyle();
		int encoder24Style = CurrentBank.encoders[24].getEncoderStyle();
		int encoder01Style = CurrentBank.encoders[1].getEncoderStyle();

		final int[] encoderStylesOrderedByCc = CurrentBank
				.getEncoderStylesOrderedByCc();
		EventProcessorMidiMessage initialiseLEDRingStyle = LividMessageFactory
				.buildLED_Ring_Style(encoderStylesOrderedByCc);

		assertRingStyle(encoder00Style, initialiseLEDRingStyle, 6);
		assertRingStyle(encoder08Style, initialiseLEDRingStyle, 7);
		assertRingStyle(encoder16Style, initialiseLEDRingStyle, 8);
		assertRingStyle(encoder24Style, initialiseLEDRingStyle, 9);
		assertRingStyle(encoder01Style, initialiseLEDRingStyle, 10);
	}

	public void assertRingStyle(int expectedRingStyle,
			EventProcessorMidiMessage initialiseLEDRingStyle, int sysexIndex) {
		assertEquals(expectedRingStyle, initialiseLEDRingStyle
				.getAsSysexMessage().getMessage()[sysexIndex]);
	}
}
