package decorps.eventprocessor;

import static decorps.eventprocessor.utils.BaseUtils.logoutMidiMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.rules.PauseBeforeSend;

public class RulesAwareReceiverWrapper implements Receiver {
	private final Receiver receiver;
	private final List<EventProcessorMidiMessage> midiMessages = new ArrayList<EventProcessorMidiMessage>();
	public final Set<Action> actions;

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
		midiMessages.clear();
		EventProcessorShortMessage eventProcessorShortMessage = EventProcessorShortMessage
				.build(message);
		if (actions.isEmpty()) {
			eventProcessorShortMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProcessorShortMessage);
			return;
		}

		for (Action action : actions) {
			System.out.println("Receiving ..."
					+ logoutMidiMessage(eventProcessorShortMessage));
			if (!action.shouldTriggerOn(eventProcessorShortMessage))
				continue;
			System.out.print("will react upon receiving "
					+ action.tetraParameter.name());
			EventProcessorMidiMessage eventProcessorMidiMessage = action.rule
					.transform(eventProcessorShortMessage);
			System.out.println(" by sending "
					+ logoutMidiMessage(eventProcessorMidiMessage));
			if (action.rule instanceof PauseBeforeSend)
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new EventProcessorException(e);
				}
			eventProcessorMidiMessage.send(receiver, timeStamp);
			this.midiMessages.add(eventProcessorMidiMessage);
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
