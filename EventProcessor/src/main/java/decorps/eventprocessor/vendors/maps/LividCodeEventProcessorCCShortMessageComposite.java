package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.vendors.livid.LividCodeEventProcessorCCShortMessage;

public class LividCodeEventProcessorCCShortMessageComposite {
	List<LividCodeEventProcessorCCShortMessage> lividCodeEventProcessorCCShortMessage = new ArrayList<LividCodeEventProcessorCCShortMessage>(
			400);

	public List<LividCodeEventProcessorCCShortMessage> getLividCodeEventProcessorCCShortMessageList() {
		return lividCodeEventProcessorCCShortMessage;
	}

	public void add(LividCodeEventProcessorCCShortMessage map) {
		lividCodeEventProcessorCCShortMessage.add(map);
	}

}
