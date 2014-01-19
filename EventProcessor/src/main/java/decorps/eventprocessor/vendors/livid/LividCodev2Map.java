package decorps.eventprocessor.vendors.livid;

import javax.sound.midi.MidiDevice.Info;

public class LividCodev2Map {
	private static final String VENDOR = "Livid Instruments, Inc.";
	private static final String NAME = "Code - Controls";

	public static boolean isCodeV2(Info info) {
		return NAME.equals(info.getName()) && VENDOR.equals(info.getVendor());
	}
}
