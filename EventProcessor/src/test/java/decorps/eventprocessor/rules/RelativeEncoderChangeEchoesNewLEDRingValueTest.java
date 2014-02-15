package decorps.eventprocessor.rules;

import static decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest.getRandomByte;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import javax.sound.midi.ShortMessage;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.livid.Encoder;
import decorps.eventprocessor.vendors.livid.Mode;
import decorps.eventprocessor.vendors.maps.EventProcessorMap;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class RelativeEncoderChangeEchoesNewLEDRingValueTest {
	RelativeEncoderChangeEchoesNewLEDRingValue cut = null;

	@Before
	public void initialise() {
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;
		cut = new RelativeEncoderChangeEchoesNewLEDRingValue();
		MapRepository.initialise();
	}

	@Test
	public void whenaNRPNMessageIsSentToTetra_ifIsRelative_SendCorrespondingCcToLivid()
			throws Exception {

		int ccNumberAssociatedToARelativeEncoder = getCcNumberAssociatedToaRelativeEncoder();
		assertThat("should find at least one relative encoder",
				ccNumberAssociatedToARelativeEncoder, greaterThan(0));

		final int relativeValue = getRandomByte();
		EventProcessorMidiMessage ccComingOutOfLivid = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE, 0,
						ccNumberAssociatedToARelativeEncoder, relativeValue);

		EventProcessorMidiMessage result = cut.transform(ccComingOutOfLivid);

		assertEquals(ccNumberAssociatedToARelativeEncoder, result
				.getAsShortMessage().getData1());
		assertFalse(relativeValue == result.getAsShortMessage().getData2());
	}

	@Test
	public void givenOsc1FineTune_whenTurnedToMax_echoesRebasedCcToLivid()
			throws Exception {
		Encoder oscFineTuneEncoder = ControllerRepository
				.getEncoderForParameterClass(Osc1FineFreq.class);
		assertNotNull(oscFineTuneEncoder);

		oscFineTuneEncoder.incrementUntil((byte) 100);
		EventProcessorShortMessage result = EventProcessorShortMessage
				.buildShortMessage(oscFineTuneEncoder).getAsShortMessage();

		assertEquals(127, result.getData2());
	}

	private int getCcNumberAssociatedToaRelativeEncoder() {
		int cc = 1;
		for (EventProcessorMap map : MapRepository.maps) {
			for (Controller controller : map.getControllers()) {
				if (Mode.Absolute == controller.getMode()
						|| !(controller instanceof Encoder))
					continue;
				System.out.println("has picked " + controller);
				return controller.getCCOrNoteNumber();
			}
		}
		return cc;
	}

	@Test
	public void getCcNumberAssociatedToaRelativeEncoderTest() throws Exception {
		assertThat(getCcNumberAssociatedToaRelativeEncoder(), greaterThan(0));
	}
}
