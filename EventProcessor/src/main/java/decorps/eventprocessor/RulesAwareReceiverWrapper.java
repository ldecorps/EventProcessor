package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.rules.Rule;

public class RulesAwareReceiverWrapper implements Receiver {
	private final Set<Rule> rules = new HashSet<Rule>();
	private final Receiver receiver;
	private final List<MidiMessage> midiMessages = new ArrayList<MidiMessage>();

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
		if (rules.isEmpty()) {
			eventProvessorShortMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProvessorShortMessage);
			return;
		}

		for (Rule rule : rules) {
			eventProvessorShortMessage = rule
					.transform(eventProvessorShortMessage);
			eventProvessorShortMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProvessorShortMessage);
		}
	}

	public void add(Rule rule) {
		rules.add(rule);
	}

	public MidiMessage getSentMidiMessage() {
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

}
