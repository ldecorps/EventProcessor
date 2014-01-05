package decorps.eventprocessor.vendors.akai;

import javax.sound.midi.MidiDevice.Info;

public class AkaiMap {

	public static boolean isLpk25(Info info) {
		final boolean result = "AKAI professional LLC".equals(info.getVendor())
				&& "LPK25".equals(info.getName());
		return result;
	}

}
