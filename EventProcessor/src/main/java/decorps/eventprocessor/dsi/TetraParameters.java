package decorps.eventprocessor.dsi;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.EventProcessorShortMessage;
import decorps.eventprocessor.utils.BaseUtils;

public enum TetraParameters {
	Oscillator1Frequency {
		@Override
		public boolean is(EventProcessorShortMessage eventProcessorShortMessage) {
			throw new EventProcessorException("Not Implemented Yet");
		}
	},
	Oscillator2FineTune {
		@Override
		public boolean is(EventProcessorShortMessage eventProcessorShortMessage) {
			throw new EventProcessorException("Not Implemented Yet");
		}
	},
	ProgramChange {
		@Override
		public boolean is(EventProcessorShortMessage eventProcessorShortMessage) {
			return BaseUtils.MsbEquals("1100",
					eventProcessorShortMessage.shortMessage.getStatus())
					&& eventProcessorShortMessage.getData2() == 0;
		}
	},
	ANY_MESSAGE {
		@Override
		public boolean is(EventProcessorShortMessage eventProcessorShortMessage) {
			return true;
		}
	};

	abstract public boolean is(
			EventProcessorShortMessage eventProcessorShortMessage);
}
