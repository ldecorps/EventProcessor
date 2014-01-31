package decorps.eventprocessor.rules;

import java.util.HashMap;
import java.util.Map;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.OscillatorMix;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;
import decorps.eventprocessor.vendors.maps.DefaultMap;
import decorps.eventprocessor.vendors.maps.OscillatorButton_to_OscillatorShapeMap;

public class LividEncoderOrButtonValueNewValue_SentToTetra implements Rule {
	final Map<Integer, DefaultMap> maps = new HashMap<Integer, DefaultMap>();

	public LividEncoderOrButtonValueNewValue_SentToTetra() {
		registerMap(new OscillatorButton_to_OscillatorShapeMap(
				Oscillator1Shape.class, 33));
		registerMap(new DefaultMap(OscillatorMix.class, 11));
	}

	public void registerMap(DefaultMap map) {
		maps.put((int) map.controllerNumber, map);
	}

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		final DefaultMap map = getMapFor(eventProcessorMidiMessage);
		return map.mapFromLividToTetra(eventProcessorMidiMessage);
	}

	private DefaultMap getMapFor(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		final int controllerNumber = eventProcessorMidiMessage
				.getAsShortMessage().getData1();
		return maps.get(controllerNumber);
	}
}
