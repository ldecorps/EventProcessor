package decorps.eventprocessor;

import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Transmitter;

import decorps.eventprocessor.dsi.TetraParameters;
import decorps.eventprocessor.rules.Rule;

public class Link {
	private static final long TIME_WHEN_NOT_OPENED = -1L;
	public final RulesAwareReceiverWrapper receiver;
	public final Transmitter transmitter;
	private long timeWhenOpened;
	public final Set<Action> actions = new HashSet<Action>();

	private Link(RulesAwareReceiverWrapper receiver, Transmitter transmitter) {
		this.timeWhenOpened = TIME_WHEN_NOT_OPENED;
		this.receiver = receiver;
		this.transmitter = transmitter;
	}

	public static Link build(RulesAwareReceiverWrapper receiver,
			Transmitter transmitter) {
		return new Link(receiver, transmitter);
	}

	public void open() {
		transmitter.setReceiver(receiver);
		timeWhenOpened = System.currentTimeMillis();
	}

	public void close() {
		timeWhenOpened = TIME_WHEN_NOT_OPENED;
		transmitter.setReceiver(null);
	}

	public boolean isOpen() {
		return TIME_WHEN_NOT_OPENED == timeWhenOpened;
	}

	public long getMicrosecondPosition() {
		return System.currentTimeMillis() - timeWhenOpened;
	}

	public void register(Rule rule, TetraParameters tetraParameter) {
		actions.add(Action.build(rule, tetraParameter));
	}
}