package decorps.eventprocessor;

import java.util.Set;

import javax.sound.midi.Transmitter;

public class Link {
	private static final long TIME_WHEN_NOT_OPENED = -1L;
	public final RulesAwareReceiverWrapper receiver;
	public final Transmitter transmitter;
	private long timeWhenOpened;

	private Link(RulesAwareReceiverWrapper in, Transmitter out) {
		this.timeWhenOpened = TIME_WHEN_NOT_OPENED;
		this.receiver = in;
		this.transmitter = out;
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

	public Set<Action> getActions() {
		return receiver.actions;
	}

}