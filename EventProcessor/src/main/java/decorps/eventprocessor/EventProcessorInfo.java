package decorps.eventprocessor;

import javax.sound.midi.MidiDevice.Info;

public class EventProcessorInfo extends Info {

	public EventProcessorInfo(String name, String vendor, String description,
			String version) {
		super(name, vendor, description, version);
	}

}
