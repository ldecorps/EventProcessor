package decorps.eventprocessor.vendors.livid.messages;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorShortMessage;

public class LocalControl extends EventProcessorShortMessage {

	public LocalControl(int value) {
		try {
			super.setMessage(ShortMessage.CONTROL_CHANGE, 15, 122, 122);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

}
