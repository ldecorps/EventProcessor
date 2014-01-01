package decorps.eventprocessor;

import decorps.eventprocessor.dsi.TetraParameters;
import decorps.eventprocessor.rules.Rule;

public class Action {

	public final Rule rule;
	public final TetraParameters tetraParameter;

	public Action(Rule rule, TetraParameters tetraParameter) {
		super();
		this.rule = rule;
		this.tetraParameter = tetraParameter;
	}

	public static Action build(Rule rule, TetraParameters tetraParameter) {
		return new Action(rule, tetraParameter);
	}

	public boolean shouldTriggerOn(
			EventProcessorMidiMessage eventProvessorMidiMessage) {
		return eventProvessorMidiMessage.is(tetraParameter);
	}

}
