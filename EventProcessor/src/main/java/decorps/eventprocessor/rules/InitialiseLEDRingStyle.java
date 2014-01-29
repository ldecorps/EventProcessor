package decorps.eventprocessor.rules;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class InitialiseLEDRingStyle implements Rule {

	public static final int WALK = 0;
	public static final int FILL = 1;
	public static final int SPREAD = 2;
	public static final int EQ = 3;

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		throw new EventProcessorException("Not Implemented Yet");
	}

	EventProcessorMidiMessage buildLEDRingStyle(int... payload) {
		return LividMessageFactory.buildLED_Ring_Style(payload);
	}

}
