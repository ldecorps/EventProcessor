package decorps.eventprocessor.rules;

import static decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest.getRandomByte;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import javax.sound.midi.ShortMessage;

import org.junit.Ignore;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.Mode;
import decorps.eventprocessor.vendors.maps.EventProcessorMap;
import decorps.eventprocessor.vendors.maps.MapRepository;

@Ignore
// TODO needs one relative parameter mapped
public class RelativeEncoderChangeEchoesNewLEDRingValueTest {

	RelativeEncoderChangeEchoesNewLEDRingValue cut = new RelativeEncoderChangeEchoesNewLEDRingValue();

	@Test
	public void whenaNRPNMessageIsSentToTetra_ifIsRelative_SendCorrespondingCcToLivid()
			throws Exception {

		int ccNumberAssociatedToARelativeEncoder = getCcNumberAssociatedToaRelativeEncoder();

		final int relativeValue = getRandomByte();
		EventProcessorMidiMessage ccComingOutOfLivid = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE, 0,
						ccNumberAssociatedToARelativeEncoder, relativeValue);

		EventProcessorMidiMessage result = cut.transform(ccComingOutOfLivid);

		assertEquals(ccNumberAssociatedToARelativeEncoder, result
				.getAsShortMessage().getData1());
		assertFalse(relativeValue == result.getAsShortMessage().getData2());
	}

	private int getCcNumberAssociatedToaRelativeEncoder() {
		int cc = 0;
		for (EventProcessorMap map : MapRepository.maps) {
			for (Controller controller : map.getControllers()) {
				if (Mode.Absolute == controller.getMode())
					continue;
				return controller.getCCNumber();
			}
		}
		return cc;
	}

	@Test
	public void getCcNumberAssociatedToaRelativeEncoderTest() throws Exception {
		assertThat(getCcNumberAssociatedToaRelativeEncoder(), greaterThan(0));
	}
}
