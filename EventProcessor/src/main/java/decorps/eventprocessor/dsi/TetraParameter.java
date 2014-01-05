package decorps.eventprocessor.dsi;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.utils.BaseUtils;

public enum TetraParameter {
	Oscillator1Frequency {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			throw new EventProcessorException("Not Implemented Yet");
		}
	},
	Oscillator2FineTune {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			throw new EventProcessorException("Not Implemented Yet");
		}
	},
	ProgramChange {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			return BaseUtils.MsbEquals("1100", eventProcessorMidiMessage
					.getAsShortMessage().getStatus())
					&& eventProcessorMidiMessage.getAsShortMessage().getData2() == 0;
		}
	},
	ANY_MESSAGE {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			return true;
		}
	};

	abstract public boolean is(
			EventProcessorMidiMessage eventProcessorMidiMessage);
}
