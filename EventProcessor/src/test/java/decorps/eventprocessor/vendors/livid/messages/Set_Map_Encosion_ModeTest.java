package decorps.eventprocessor.vendors.livid.messages;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;

import org.junit.Test;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.EventProcessorTest;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;

public class Set_Map_Encosion_ModeTest {
	@Test
	public void setEncoderEncosion() throws Exception {
		EventProcessor envProcessor = EventProcessorTest
				.getInstanceWithoutActions();
		final EventProcessorMidiMessage encosion = LividMessageFactory
				.buildMap_Encosion_Mode(binaryToByte("0000 0011"));
		encosion.send(envProcessor.fromTetraToLivid.receiver, -1);
	}
}
