package decorps.eventprocessor.rules;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.maps.EventProcessorMap;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class LividEncoderOrButtonValue_NewValue_SendToTetra implements Rule {

	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		final Controller controllerForLividShortMessage = ControllerRepository
				.getControllerForLividShortMessage(eventProcessorMidiMessage);

		List<EventProcessorMap> maps = MapRepository
				.getMapsForController(controllerForLividShortMessage);
		List<EventProcessorMidiMessage> messages = new ArrayList<EventProcessorMidiMessage>();
		for (EventProcessorMap map : maps) {
			System.out.println("using map: " + map);
			messages.add(map.mapLividCcToTetraNrpn(eventProcessorMidiMessage));
		}
		EventProcessorMidiMessage result = EventProcessorMidiMessageComposite
				.buildComposite(messages);
		return result;

	}

	void setNewRelativeValue(Controller controller,
			ProgramParameter programParameter, final byte valueReceived) {
		boolean increment = 1 == valueReceived;
		final byte currentValue = programParameter.getValue();
		byte newValue = 0;
		if (increment)
			newValue = increment(currentValue, programParameter);
		else
			newValue = decrement(currentValue);
		programParameter.setValue(controller, newValue);
	}

	private byte decrement(final byte currentValue) {
		byte newValue = currentValue;
		if (currentValue > 0)
			newValue--;
		return newValue;
	}

	private byte increment(final byte currentValue,
			ProgramParameter programParameter) {
		byte newValue = currentValue;
		if (programParameter.getRebasedValue() != 127)
			newValue++;
		return newValue;
	}
}
