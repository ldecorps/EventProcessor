package decorps.eventprocessor.vendors.livid.messages;

import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorShortMessage;

public class Set_LED_Ring_Mode extends EventProcessorMidiMessageComposite {

	public static final int WALK = 0;
	public static final int FILL = 1;
	public static final int EQ = 2;
	public static final int SPREAD = 3;

	public Set_LED_Ring_Mode(int... payload) {
		if (128 < payload.length)
			throw new EventProcessorException("too many arguments");
		for (int i = 0; i < payload.length; i++) {
			addCCFor(i, payload);
		}
	}

	private void addCCFor(int i, int... payload) {
		int bank = getBankFor(i);
		final int CCNumber = i - (33 * bank) + 33;
		final int velocity = payload[i] + 64 + (bank * 16);
		EventProcessorMidiMessage message = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE, 15, CCNumber,
						velocity);
		super.eventProcessorMidiMessages.add(message);
	}

	private int getBankFor(int i) {
		if (i > 96)
			return 3;
		if (i > 64)
			return 2;
		if (i > 32)
			return 1;
		return 0;
	}
}
