package decorps.eventprocessor;

import java.util.Set;

import javax.sound.midi.Transmitter;

public class Link {
	public final RulesAwareReceiverWrapper receiver;
	public final Transmitter transmitter;

	private Link(RulesAwareReceiverWrapper in, Transmitter out) {
		this.receiver = in;
		this.transmitter = out;
	}

	public static Link build(RulesAwareReceiverWrapper receiver,
			Transmitter transmitter) {
		final Link link = new Link(receiver, transmitter);
		link.setReceiver();
		return link;
	}

	private void setReceiver() {
		transmitter.setReceiver(receiver);
	}

	public Set<Action> getActions() {
		return receiver.actions;
	}

}