package decorps.eventprocessor;

import static decorps.eventprocessor.utils.BaseUtils.LINE_SEPARATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.rules.PauseBeforeSend;
import decorps.eventprocessor.utils.BaseUtils;

public class RulesAwareReceiverWrapper implements Receiver {
	private final Receiver receiver;
	private final List<EventProcessorMidiMessage> midiMessages = new ArrayList<EventProcessorMidiMessage>();
	public final Set<Action> actions;
	public static Object wait = new Object();

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
		System.out.println("About to send " + BaseUtils.decodeMessage(message)
				+ LINE_SEPARATOR
				+ BaseUtils.bytesToBinary(message.getMessage()));
		midiMessages.clear();
		EventProcessorMidiMessage eventProcessorMidiMessage = EventProcessorMidiMessage
				.build(message);
		if (actions.isEmpty()) {
			eventProcessorMidiMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProcessorMidiMessage);
			return;
		}

		for (Action action : actions) {
			synchronized (wait) {
				wait.notify();
			}
			if (!action.shouldTriggerOn(eventProcessorMidiMessage))
				continue;
			System.out.print("will react upon receiving "
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
		}
	}

	public EventProcessorShortMessage getSentMidiMessage() {
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
