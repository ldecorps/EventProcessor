package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.utils.BaseUtils.checkSizeIsNotMoreThan;
import static decorps.eventprocessor.utils.MidiUtils.buildSysexMessage;

import javax.sound.midi.MidiDevice.Info;

public class LividCodev2Map {
	private static final String VENDOR = "Livid Instruments, Inc.";
	private static final String NAME = "Code - Controls";
	public static final byte[] LividCode2Signature = new byte[] { 0x0, 0x01,
			0x61, 0x04 };
	private static int signatureLength = LividCode2Signature.length;

	static final byte[] addSpecificLividCode2Payload(byte[] originalMessage,
			int[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			originalMessage[i + signatureLength + 1 + 1] = (byte) bytes[i];
		}
		return originalMessage;
	}

	static final byte[] buildEmptyLivideCode2Sysex(int size) {
		int exclusiveByte = 1, specificByte = 1, endOfExclusiveByte = 1;
		byte[] result = buildSysexMessage(exclusiveByte + signatureLength
				+ specificByte + size + endOfExclusiveByte, LividCode2Signature);
		return result;
	}

	public static final byte[] buildSet_all_LED_indicators(int... payload) {
		return buildWithSpecificByteAndPayloadSize(payload, 0x04, 8);
	}

	public static final byte[] buildSet_LED_Ring_indicators(int... payload) {
		return buildWithSpecificByteAndPayloadSize(payload, 0x1f, 64);
	}

	public static final byte[] buildRequest_all_LED_indicators() {
		return buildWithSpecificByteAndPayloadSize(new int[] { 0x04 }, 0x07, 1);
	}

	static byte[] buildWithSpecificByteAndPayloadSize(int[] bytes,
			int specifyingBit, int maximumLength) {
		checkSizeIsNotMoreThan(maximumLength, bytes);
		byte[] result = buildSpecificLividCode2Sysex(maximumLength,
				specifyingBit);
		result = addSpecificLividCode2Payload(result, bytes);
		return result;
	}

	static final byte[] buildSpecificLividCode2Sysex(int size, int specifyingBit) {
		byte[] result = buildEmptyLivideCode2Sysex(size);
		result[signatureLength + 1] = (byte) specifyingBit;
		return result;
	}

	public static boolean isCodeV2(Info info) {
		return NAME.equals(info.getName()) && VENDOR.equals(info.getVendor());
	}
}
