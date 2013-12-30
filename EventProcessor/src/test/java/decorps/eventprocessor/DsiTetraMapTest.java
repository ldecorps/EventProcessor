package decorps.eventprocessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.SysexMessage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Ignore;
import org.junit.Test;

public class DsiTetraMapTest {

	final SysexMessage sampleMsg = EventProcessorTest
			.getSampleProgramDataDumpSysexMessage();
	final DsiTetraMap cut = new DsiTetraMap();
	EventProcessorShortMessageComposite result = cut.convert(sampleMsg);

	@Test
	public void newMap_shouldMap_Oscillator1Frequency() throws Exception {
		EventProcessorShortMessageComposite result = cut.convert(sampleMsg);
		assertThat(result.get(0).getData1(),
				is(TetraParameters.Oscillator1Frequency.parm));
	}

	@Test
	public void convertStringToByte() throws Exception {
		checkStringToByte("0000 0000", 0);
		checkStringToByte("0000 0001", 1);
		checkStringToByte("0000 1111", 15);
		checkStringToByte("0001 0000", 16);
		checkStringToByte("0010 0000", 32);
		checkStringToByte("0100 0000", 64);
		checkStringToByte("1000 0000", 128);
		checkStringToByte("1111 1111", 255);
	}

	@Test
	public void convertOneByteToString() throws Exception {
		checkBinaryToString(0, "0000 0000");
		checkBinaryToString(1, "0000 0001");
		checkBinaryToString(2, "0000 0010");
		checkBinaryToString(3, "0000 0011");
		checkBinaryToString(4, "0000 0100");
		checkBinaryToString(5, "0000 0101");
		checkBinaryToString(6, "0000 0110");
		checkBinaryToString(7, "0000 0111");
		checkBinaryToString(8, "0000 1000");
		checkBinaryToString(9, "0000 1001");
		checkBinaryToString(10, "0000 1010");
		checkBinaryToString(11, "0000 1011");
		checkBinaryToString(12, "0000 1100");
		checkBinaryToString(13, "0000 1101");
		checkBinaryToString(14, "0000 1110");
		checkBinaryToString(15, "0000 1111");
		checkBinaryToString(16, "0001 0000");
		checkBinaryToString(32, "0010 0000");
		checkBinaryToString(64, "0100 0000");
		checkBinaryToString(128, "1000 0000");
		checkBinaryToString(255, "1111 1111");
	}

	void checkBinaryToString(int oneByte, String representation) {
		assertThat(DsiTetraMap.byteToBinary((byte) oneByte), is(representation));
	}

	void checkStringToByte(String representation, int oneByte) {
		assertThat(DsiTetraMap.binaryToByte(representation), is((byte) oneByte));
	}

	@Test
	public void canRecogniseTetraProgramDump() throws Exception {
		assertTrue(cut.isValidTetraProgramDump());
	}

	@Test
	@Ignore
	public void hasPackedDataFormat() throws Exception {
		byte[] sysexMessage = sampleMsg.getData();
		assertTrue(cut.isEndOfExclusive(sysexMessage[sysexMessage.length - 1]));
		byte[] sysexMessageMinusEndOfExclusive = ArrayUtils.subarray(
				sysexMessage, 0, sysexMessage.length - 1);
		assertTrue(sysexMessageMinusEndOfExclusive.length == sysexMessage.length - 1);
		assertThat(sysexMessageMinusEndOfExclusive[2],
				is(DsiTetraMap.Program_Data));
		byte[] sysexMessageMinusEndOfExclusiveMinusFiveFirstBytes = ArrayUtils
				.subarray(sysexMessageMinusEndOfExclusive, 5,
						sysexMessageMinusEndOfExclusive.length);
		byte[] next8BytePackets = ArrayUtils.subarray(
				sysexMessageMinusEndOfExclusiveMinusFiveFirstBytes, 0, 64);

		DsiTetraMap.printOutBytesAsHexa(next8BytePackets);
		DsiTetraMap.printOutBinaryMessage(next8BytePackets);
		for (int i = 0; i < 8; i++)
			assertEquals(
					"row "
							+ (i + 1)
							+ " is "
							+ DsiTetraMap
									.byteToBinary(MSB(next8BytePackets[i * 8])),
					0, MSB(next8BytePackets[i * 8]));
	}

	private byte MSB(byte currentByte) {
		String byteAsBinary = DsiTetraMap.byteToBinary(currentByte);
		return Byte.parseByte(byteAsBinary.substring(0, 4), 2);
	}

	@Test
	public void Hexa18Is11000() throws Exception {
		assertEquals(
				DsiTetraMap.byteToHex(DsiTetraMap.binaryToByte("0001 1000")),
				"18");
	}

	@Test
	public void canRecognizeTetraFromInfo() throws Exception {
		assertTrue(DsiTetraMap.isTetra(new TestingDSITetraInfo()));
	}

	@Test
	public void canRecognizeAProgramChangeForTetra() throws Exception {
		throw new NotImplementedException();
	}
}
