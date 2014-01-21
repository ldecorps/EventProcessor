package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public class LividCodeEventProcessorCCShortMessageComposite extends
		EventProcessorMidiMessageComposite {

	@Override
	public String toString() {
		return "LividCodeEventProcessorCCShortMessageComposite [lividCodeEventProcessorCCShortMessage="
				+ lividCodeEventProcessorCCShortMessage + "]";
	}

	List<EventProcessorMidiMessage> lividCodeEventProcessorCCShortMessage = new ArrayList<EventProcessorMidiMessage>(
			400);

	public List<EventProcessorMidiMessage> getMessages() {
		return lividCodeEventProcessorCCShortMessage;
	}

	public void add(LividCodeEventProcessorCCShortMessage map) {
		lividCodeEventProcessorCCShortMessage.add(map);
	}

}
