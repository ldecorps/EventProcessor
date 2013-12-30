package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

public class EventProcessorShortMessageComposite extends
		EventProcessorShortMessage {
	List<EventProcessorShortMessage> eventProcessorShortMessages = new ArrayList<EventProcessorShortMessage>();

	EventProcessorShortMessageComposite(ShortMessage shortMessage) {
		super(null);
	}

	protected EventProcessorShortMessageComposite(SysexMessage message) {
		EventProcessorShortMessageComposite eventProcessorShortMessage = dsiTetraMap
				.convert(message);
		eventProcessorShortMessages
				.addAll(eventProcessorShortMessage.eventProcessorShortMessages);
	}

	public void add(EventProcessorShortMessage eventProcessorShortMessage) {
		eventProcessorShortMessages.add(eventProcessorShortMessage);
	}

	public static EventProcessorShortMessageComposite build() {
		return new EventProcessorShortMessageComposite(new ShortMessage());
	}

	public EventProcessorShortMessage get(int i) {
		return eventProcessorShortMessages.get(i);
	}

	@Override
	public void send(Receiver receiver, long timestamp) {
		for (EventProcessorShortMessage eventProcessorShortMessage : eventProcessorShortMessages)
			receiver.send(eventProcessorShortMessage, timestamp);
	}
}
