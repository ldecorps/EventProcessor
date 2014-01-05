package decorps.eventprocessor;

import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.Rule;

public class Action {

	public final Rule rule;
	public final TetraParameter tetraParameter;
	public final Transmitter in;
	public final Receiver out;

	public Action(Rule rule, TetraParameter tetraParameter, Transmitter in,
			Receiver out) {
		super();
		this.rule = rule;
		this.tetraParameter = tetraParameter;
		this.in = in;
		this.out = out;
	}

	public static Action build(Rule rule, TetraParameter tetraParameter,
			Transmitter in, Receiver out) {
		return new Action(rule, tetraParameter, in, out);
	}

	public static Action build(Rule rule, TetraParameter tetraParameter,
			Link link) {
		return build(rule, tetraParameter, link.transmitter, link.receiver);
	}

	public boolean shouldTriggerOn(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		return eventProvessorMidiMessage.is(tetraParameter);
	}

}
