package decorps.eventprocessor.vendors.dsi;

import decorps.eventprocessor.exceptions.EventProcessorException;
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
	},
	ProgramEditBufferDataDump {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			final boolean result = DsiTetraMap
					.isProgramEditBufferDataDump(eventProcessorMidiMessage
							.getMessage());
			return result;
		}
	},
	ProgramDataDump {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			final boolean result = DsiTetraMap
					.isProgramDataDump(eventProcessorMidiMessage.getMessage());
			return result;
		}
	},
	ANY_NOTE {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			return eventProcessorMidiMessage.isNote();
		}
	},
	ANY_NOTE_OR_CC {
		@Override
		public boolean is(EventProcessorMidiMessage eventProcessorMidiMessage) {
			return eventProcessorMidiMessage.isNote()
					|| eventProcessorMidiMessage.isCC();
		}
	};

	abstract public boolean is(
			EventProcessorMidiMessage eventProcessorMidiMessage);
}
