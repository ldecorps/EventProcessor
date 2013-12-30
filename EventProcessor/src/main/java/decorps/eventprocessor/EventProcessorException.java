package decorps.eventprocessor;

import javax.sound.midi.InvalidMidiDataException;

@SuppressWarnings("serial")
public class EventProcessorException extends RuntimeException {

	public EventProcessorException(Exception e) {
		super(e);
	}

	public EventProcessorException(String message, InvalidMidiDataException e) {
		super(message, e);
	}

	public EventProcessorException(String message) {
		super(message);
	}

}
