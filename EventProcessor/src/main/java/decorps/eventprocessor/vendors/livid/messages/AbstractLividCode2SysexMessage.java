package decorps.eventprocessor.vendors.livid.messages;

import static decorps.eventprocessor.utils.BaseUtils.checkSizeIsNotMoreThan;
import decorps.eventprocessor.messages.EventProcessorSysexMessage;
import decorps.eventprocessor.utils.MidiUtils;

public abstract class AbstractLividCode2SysexMessage extends
		EventProcessorSysexMessage {
	public static final byte[] LividCode2Signature = new byte[] { 0x0, 0x01,
			0x61, 0x04 };
	private static int signatureLength = LividCode2Signature.length;

	public AbstractLividCode2SysexMessage(int[] data, int specificByte,
			int maximumLength) {
		super(buildWithSpecificByteAndPayloadSize(data, specificByte,
				maximumLength));
	}

	static byte[] buildWithSpecificByteAndPayloadSize(int[] bytes,
			int specifyingBit, int maximumLength) {
		checkSizeIsNotMoreThan(maximumLength, bytes);
		byte[] result = buildSpecificLividCode2Sysex(maximumLength,
				specifyingBit);
		result = addSpecificLividCode2Payload(result, bytes);
		return result;
	}

	final static byte[] buildSpecificLividCode2Sysex(int size, int specifyingBit) {
		byte[] result = buildEmptyLivideCode2Sysex(size);
		result[signatureLength + 1] = (byte) specifyingBit;
		return result;
	}

	final static byte[] buildEmptyLivideCode2Sysex(int size) {
		int exclusiveByte = 1, specificByte = 1, endOfExclusiveByte = 1;
		byte[] result = MidiUtils.buildSysexMessage(exclusiveByte
				+ signatureLength + specificByte + size + endOfExclusiveByte,
				LividCode2Signature);
		return result;
	}

	final static byte[] addSpecificLividCode2Payload(byte[] originalMessage,
			int[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			originalMessage[i + signatureLength + 1 + 1] = (byte) bytes[i];
		}
		return originalMessage;
	}
}
