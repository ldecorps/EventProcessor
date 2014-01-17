package decorps.eventprocessor.messages;

import javax.sound.midi.MidiMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;

public class EventProcessorMidiMessageComposite extends
		EventProcessorMidiMessage {
	public static final byte[] NullCompositeMessageByteArray = new byte[] {};
	public final EventProcessorMidiMessage[] eventProcessorMidiMessages;

	protected EventProcessorMidiMessageComposite(
			EventProcessorMidiMessage... eventProcessorMidiMessages) {
		super(NullCompositeMessageByteArray);
		this.eventProcessorMidiMessages = eventProcessorMidiMessages;

	}

	public static EventProcessorMidiMessage buildComposite(
			EventProcessorMidiMessage... eventProcessorMidiMessages) {
		return new EventProcessorMidiMessageComposite(
				eventProcessorMidiMessages);
	}

	@Override
	protected MidiMessage getMidiMessage() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public Object clone() {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
