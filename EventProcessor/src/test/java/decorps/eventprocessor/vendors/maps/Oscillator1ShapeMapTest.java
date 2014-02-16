package decorps.eventprocessor.vendors.maps;

import static decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest.getRandomByteOtherThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;

public class Oscillator1ShapeMapTest {
	Oscillator1ShapeMap cut;

	@Before
	public void createMap() {
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;

		cut = new Oscillator1ShapeMap();
	}

	@Test
	public void zeroFromTetra_to_Button_Velocity0() throws Exception {
		final Controller button = Oscillator1ShapeMap.getOnOffButton();
		assertThat((int) button.getValue(), not(0));

		ProgramParameterData.CurrentLayer.oscillator1Shape.setValue(button,
				(byte) 0);

		assertEquals(0, button.getRebasedValue());
	}

	@Test
	public void notZeroFromTetra_to_Button_Velocity64() throws Exception {
		final Controller button = Oscillator1ShapeMap.getOnOffButton();
		final ProgramParameter oscillator1Shape = ProgramParameterData.CurrentLayer.oscillator1Shape;
		oscillator1Shape.setValue(button, (byte) 0);

		final byte randomByteOtherThan = getRandomByteOtherThan((byte) 0);
		oscillator1Shape.setValue(button, randomByteOtherThan);

		assertEquals(64, button.getRebasedValue());
	}

	@Test
	public void button8_setSawtoothOn() throws Exception {
		cut.switchTriangleButtonOn();
		assertFalse(cut.isSawtooth());

		cut.switchSawtoothButtonOn();

		assertTrue(cut.isSawtooth());
	}
}
