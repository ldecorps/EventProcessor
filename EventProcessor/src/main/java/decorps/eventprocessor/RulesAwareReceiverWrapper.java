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

	public void send(MidiMessage message, long timeStamp) {
		EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
				.build(message);
		if (shouldFilter(eventProcessorMidiMessage))
			return;
		if (eventProcessorMidiMessage.isComposite()) {
			for (EventProcessorMidiMessage currentMessage : eventProcessorMidiMessage
					.getAsComposite().getMessages())
				send(currentMessage, timeStamp);
		} else
			doEventProcessorMidiMessage(message, eventProcessorMidiMessage);
	}

	private void doEventProcessorMidiMessage(MidiMessage message,
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		long timeStamp;
		if (shouldFilter(eventProcessorMidiMessage))
			return;
		System.out.print("Receiving " + BaseUtils.decodeMessage(message));
		System.out.println(". Reseting timestamp to -1");
		timeStamp = -1;
		midiMessages.clear();
		if (actions.isEmpty()) {
			eventProcessorMidiMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProcessorMidiMessage);
			synchronized (wait) {
				wait.notifyAll();
			}
			return;
		}
		for (Action action : actions) {
			doAction(timeStamp, eventProcessorMidiMessage, action);
		}
		synchronized (wait) {
			wait.notifyAll();
		}
	}

	boolean shouldFilter(EventProcessorMidiMessage eventProcessorMidiMessage) {
		final boolean isSystemMessage = eventProcessorMidiMessage
				.isShortMessage()
				&& eventProcessorMidiMessage.getAsShortMessage().getCommand() == 0xF0;
		if (isSystemMessage)
			return true;
		return false;
	}

	public void doAction(long timeStamp,
			EventProcessorMidiMessage eventProcessorMidiMessage, Action action) {
		if (!action.shouldTriggerOn(eventProcessorMidiMessage))
			return;
		System.out.println("will react upon receiving "
				+ action.messageType.name());
		EventProcessorMidiMessage newEventProcessorMidiMessage = action.rule
				.transform(eventProcessorMidiMessage);
		System.out.println(" by sending to " + this.getClass().getSimpleName()
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

	public void close() {
		receiver.close();
		actions.clear();
	}

	public void send(EventProcessorMidiMessage eventProcessorMidiMessage) {
		send(eventProcessorMidiMessage, -1);
	}
}
