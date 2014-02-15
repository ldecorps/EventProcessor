package decorps.eventprocessor.vendors.maps;

import static decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest.getRandomByteOtherThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Button;
import decorps.eventprocessor.vendors.livid.Controller;

public class Oscillator1ShapeMapTest {
	Oscillator1ShapeMap cut;

	@Before
	public void createMap() {
		BankLayout.programParameterData = ProgramParameterData
				.build(DsiTetraMapTest.sampleEditbufferProgramDataDump
						.getMessage());

		cut = new Oscillator1ShapeMap();
	}

	private Controller getButton() {
		final Button button = BankLayout.Bank1.buttons[0];
		return button;
	}

	@Test
	public void zeroFromTetra_to_Button_Velocity0() throws Exception {
		final Controller button = getButton();
		assertThat((int) button.getValue(), not(0));

		ProgramParameterData.CurrentLayer.oscillator1Shape.setValue(button,
				(byte) 0);

		assertEquals(0, button.getRebasedValue());

	}

	@Test
	public void notZeroFromTetra_to_Button_Velocity64() throws Exception {
		final Controller button = getButton();
		final ProgramParameter oscillator1Shape = ProgramParameterData.CurrentLayer.oscillator1Shape;
		oscillator1Shape.setValue(button, (byte) 0);

		final byte randomByteOtherThan = getRandomByteOtherThan((byte) 0);
		oscillator1Shape.setValue(button, randomByteOtherThan);

		assertEquals(64, button.getRebasedValue());

	}

}
