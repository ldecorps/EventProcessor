package decorps.eventprocessor.vendors.maps;

import static decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest.getRandomByteOtherThan;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.sound.midi.ShortMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Button;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerRepository;

public class Oscillator1ShapeMapTest {
	Oscillator1ShapeMap cut;

	@Before
	@After
	public void createMap() {
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;
		BankLayout.createFourBanks();
		MapRepository.initialise();
		cut = new Oscillator1ShapeMap();
	}

	@Test
	public void zeroFromTetra_to_Button_Velocity0() throws Exception {
		final Controller button = Oscillator1ShapeMap.getSawtoothButton();
		ProgramParameterData.CurrentLayer.oscillator1Shape.setValue(button,
				getRandomByteOtherThan((byte) 0));
		assertThat((int) button.getValue(), not(0));

		ProgramParameterData.CurrentLayer.oscillator1Shape.setValue(button,
				(byte) 0);

		assertEquals(0, button.getRebasedValue());
	}

	@Test
	public void notZeroFromTetra_to_Button_Velocity64() throws Exception {
		final ProgramParameter oscillator1Shape = ProgramParameterData.CurrentLayer.oscillator1Shape;
		oscillator1Shape.setAbsoluteValue((byte) 0);
		Button button = Oscillator1ShapeMap.getSawtoothButton();

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

	@Test
	public void button9_setsTriangleOn() throws Exception {
		cut.switchSawtoothButtonOn();
		assertFalse(cut.isTriangle());

		cut.switchTriangleButtonOn();

		assertTrue(cut.isTriangle());
	}

	@Test
	public void whenSquareButtonIsPressed_SquareIsOn() throws Exception {

		EventProcessorMidiMessage lividCc = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE, 0,
						Oscillator1ShapeMap.getSquareButton().id, (byte) 4);

		EventProcessorMidiMessage nrpn = cut.mapLividCcToTetraNrpn(lividCc);

		assertEquals(4,
				nrpn.getAsEventProcessorNRPNMessage().NRPNControllerValue);
	}

	@Test
	public void newMap_hasRegisterParameterWithButton() throws Exception {
		final List<Controller> controllers = cut.getControllers();
		Button button = controllers.get(0).asButton();
		assertThat(ControllerRepository.getButtonById(button.getId()),
				sameInstance(button));

		assertThat(
				button.getProgramParameter(),
				allOf(sameInstance(cut.programParameter),
						not(sameInstance(ProgramParameter.nullParameter))));
	}
}
