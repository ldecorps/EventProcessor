package decorps.eventprocessor.vendors.korg;

import javax.sound.midi.MidiDevice.Info;

public class KorgMap {

	public static boolean isMicroKey25(Info info) {
		final boolean result = "KORG INC.".equals(info.getVendor())
				&& "KEYBOARD".equals(info.getName())
				&& "microKEY-25 KEYBOARD".equals(info.getDescription());
		return result;
	}
}
