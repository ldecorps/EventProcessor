package decorps.eventprocessor;

import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.Rule;
import decorps.eventprocessor.vendors.dsi.MessageType;

public class Action {

	public final Rule rule;
	public final MessageType messageType;
	public final Transmitter in;
	public final Receiver out;

	Action(Rule rule, MessageType messageType, Transmitter in,
			Receiver out) {
		super();
		this.rule = rule;
		this.messageType = messageType;
		this.in = in;
		this.out = out;
	}

	public static Action build(Rule rule, MessageType messageType,
			Transmitter in, Receiver out) {
		return new Action(rule, messageType, in, out);
	}

	@Override
	public String toString() {
		return "Action [rule=" + rule + ", tetraParameter=" + messageType
				+ ", in=" + in + ", out=" + out + "]";
	}

	public static Action build(Rule rule, MessageType messageType,
			Link link) {
		return build(rule, messageType, link.transmitter, link.receiver);
	}

	public boolean shouldTriggerOn(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		return eventProvessorMidiMessage.is(messageType);
	}

}
