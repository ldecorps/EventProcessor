package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.utils.MidiUtils.addToArrayAfter;
import static decorps.eventprocessor.utils.MidiUtils.buildSysexMessage;

import javax.sound.midi.MidiDevice.Info;

import decorps.eventprocessor.SysexMessageTooLong;

public class LividCodev2Map {
	private static final String VENDOR = "Livid Instruments, Inc.";
	private static final String NAME = "Controls";

	public static final byte[] SetAllLedIndicators(int... bytes) {
		if (bytes.length > 8)
			throw new SysexMessageTooLong();
		byte[] result = buildSysexMessage(15, 0x0, 0x01, 0x61, 0x04, 0x04);
		addToArrayAfter(result, 5, bytes);
		return result;
	}

	public static boolean isCodeV2(Info info) {
		return NAME.equals(info.getName()) && VENDOR.equals(info.getVendor());
	}
}
