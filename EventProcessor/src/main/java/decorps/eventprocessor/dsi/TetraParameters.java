package decorps.eventprocessor.dsi;

import decorps.eventprocessor.EventProcessorShortMessage;
import decorps.eventprocessor.utils.BaseUtils;

public enum TetraParameters {
	Oscillator1Frequency, Oscillator2FineTune, ProgramChange;

	public boolean is(EventProcessorShortMessage eventProcessorShortMessage) {
		return BaseUtils.MsbEquals("1100",
				eventProcessorShortMessage.shortMessage.getStatus())
				&& eventProcessorShortMessage.getData2() == 0;
	}
}
