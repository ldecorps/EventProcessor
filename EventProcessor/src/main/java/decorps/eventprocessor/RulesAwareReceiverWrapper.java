package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.rules.PauseBeforeSend;
import decorps.eventprocessor.utils.BaseUtils;

public class RulesAwareReceiverWrapper implements Receiver {
	private final Receiver receiver;
	private final List<EventProcessorMidiMessage> midiMessages = new ArrayList<EventProcessorMidiMessage>();
	public final Set<Action> actions;
	public Object wait = new Object();

	protected RulesAwareReceiverWrapper(Receiver receiver, Set<Action> actions) {
		this.receiver = receiver;
		this.actions = actions;
	}

	public static RulesAwareReceiverWrapper build(Receiver receiver,
			Set<Action> actions) {
		return new RulesAwareReceiverWrapper(receiver, actions);
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		System.out.println("Receiving " + BaseUtils.decodeMessage(message));
		System.out.println("reseting timestamp to -1");
		timeStamp = -1;
		midiMessages.clear();
		EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
				.build(message);
		if (actions.isEmpty()) {
			eventProcessorMidiMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProcessorMidiMessage);
			synchronized (wait) {
				wait.notifyAll();
			}
			return;
		}
		EventProcessorMidiMessage newEventProcessorMidiMessage = eventProcessorMidiMessage;
		for (Action action : actions) {
			newEventProcessorMidiMessage = doAction(timeStamp,
					newEventProcessorMidiMessage, action);
		}
		synchronized (wait) {
			wait.notifyAll();
		}

	}

	public EventProcessorMidiMessage doAction(long timeStamp,
			EventProcessorMidiMessage eventProcessorMidiMessage, Action action) {
		if (!action.shouldTriggerOn(eventProcessorMidiMessage))
			return eventProcessorMidiMessage;
		System.out.println("will react upon receiving "
				+ action.tetraParameter.name());
		EventProcessorMidiMessage newEventProcessorMidiMessage = action.rule
				.transform(eventProcessorMidiMessage);
		System.out.println(" by sending "
				+ BaseUtils.decodeMessage(newEventProcessorMidiMessage));
		if (action.rule instanceof PauseBeforeSend)
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		newEventProcessorMidiMessage.send(receiver, timeStamp);
		this.midiMessages.add(newEventProcessorMidiMessage);
		return newEventProcessorMidiMessage;
	}

	public EventProcessorShortMessage getFirstSentMidiMessage() {
		return midiMessages.get(0).getAsShortMessage();
	}

	public List<EventProcessorMidiMessage> getSentMidiMessages() {
		return midiMessages;
	}

	public Receiver getRawReceiver() {
		return receiver;
	}

	public void registerAction(Action action) {
		actions.add(action);
	}

	@Override
	public void close() {
		receiver.close();
	}
}
