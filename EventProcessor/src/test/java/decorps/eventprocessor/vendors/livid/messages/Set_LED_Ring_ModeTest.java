package decorps.eventprocessor.vendors.livid.messages;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.EventProcessorTest;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;

public class Set_LED_Ring_ModeTest {
	@Test
	public void setLEDRingMode() throws Exception {
		EventProcessor envProcessor = EventProcessorTest
				.getInstanceWithoutActions();
		final EventProcessorMidiMessageComposite buildLED_Ring_Mode = (EventProcessorMidiMessageComposite) LividMessageFactory
				.buildSet_LED_Ring_Mode(Set_LED_Ring_Mode.WALK,
						Set_LED_Ring_Mode.EQ, Set_LED_Ring_Mode.FILL,
						Set_LED_Ring_Mode.SPREAD);
		buildLED_Ring_Mode.send(envProcessor.fromTetraToLivid.receiver, -1);
	}
}
