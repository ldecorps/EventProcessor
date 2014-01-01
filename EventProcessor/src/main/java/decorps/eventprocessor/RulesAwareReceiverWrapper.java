package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class RulesAwareReceiverWrapper implements Receiver {
	private final Receiver receiver;
	private final List<EventProcessorShortMessage> midiMessages = new ArrayList<EventProcessorShortMessage>();
	public final Set<Action> actions = new HashSet<Action>();

	protected RulesAwareReceiverWrapper(Receiver receiver) {
		this.receiver = receiver;
	}

	public static RulesAwareReceiverWrapper build(Receiver receiver) {
		return new RulesAwareReceiverWrapper(receiver);
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		midiMessages.clear();
		EventProcessorShortMessage eventProvessorShortMessage = EventProcessorShortMessage
				.build(message);
		if (actions.isEmpty()) {
			eventProvessorShortMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProvessorShortMessage);
			return;
		}

		for (Action action : actions) {
			if (action.shouldTriggerOn(eventProvessorShortMessage))
				eventProvessorShortMessage = action.rule
						.transform(eventProvessorShortMessage);
			eventProvessorShortMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProvessorShortMessage);
		}
	}

	public EventProcessorShortMessage getSentMidiMessage() {
		return midiMessages.get(0);
	}

	public Collection<? extends Object> getSentMidiMessages() {
		return midiMessages;
	}

	public Receiver getRawReceiver() {
		return receiver;
	}

	@Override
	public void close() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public void registerAction(Action action) {
		actions.add(action);
	}
}
