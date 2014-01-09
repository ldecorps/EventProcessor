package decorps.eventprocessor.vendors.dsi;

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
			return eventProcessorMidiMessage.isShortMessage()
					&& eventProcessorMidiMessage.getAsShortMessage().getData2() == 0
					&& BaseUtils.MsbEquals("1100", eventProcessorMidiMessage
							.getAsShortMessage().getStatus());
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
