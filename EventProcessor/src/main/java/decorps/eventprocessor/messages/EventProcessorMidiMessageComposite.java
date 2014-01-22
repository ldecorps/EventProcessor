package decorps.eventprocessor.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.MidiMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;

public class EventProcessorMidiMessageComposite extends
		EventProcessorMidiMessage {
	public static final byte[] NullCompositeMessageByteArray = new byte[] {};
	public final List<EventProcessorMidiMessage> eventProcessorMidiMessages = new ArrayList<EventProcessorMidiMessage>();

	protected EventProcessorMidiMessageComposite(
			EventProcessorMidiMessage... eventProcessorMidiMessages) {
		super(NullCompositeMessageByteArray);
		this.eventProcessorMidiMessages.addAll(Arrays
				.asList(eventProcessorMidiMessages));
	}

	public static EventProcessorMidiMessage buildComposite(
			EventProcessorMidiMessage... eventProcessorMidiMessages) {
		return new EventProcessorMidiMessageComposite(
				eventProcessorMidiMessages);
	}

	public static EventProcessorMidiMessage buildComposite(byte[]... messages) {
		EventProcessorMidiMessage[] eventProcessorMidiMessages = new EventProcessorMidiMessage[messages.length];
		for (int i = 0; i < messages.length; i++) {
			eventProcessorMidiMessages[i] = EventProcessorMidiMessage
					.build(messages[i]);
		}
		return buildComposite(eventProcessorMidiMessages);
	}

	@Override
	protected MidiMessage getMidiMessage() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	@Override
	public Object clone() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void add(EventProcessorMidiMessage eventProcessorMidiMessage) {
		eventProcessorMidiMessages.add(eventProcessorMidiMessage);
	}

	public List<EventProcessorMidiMessage> getMessages() {
		return eventProcessorMidiMessages;
	}
}
