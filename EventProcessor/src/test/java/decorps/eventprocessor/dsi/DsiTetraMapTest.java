package decorps.eventprocessor.dsi;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static decorps.eventprocessor.utils.BaseUtils.byteToBinary;
import static decorps.eventprocessor.utils.BaseUtils.byteToHex;
import static decorps.eventprocessor.utils.BaseUtils.printOutBinaryMessage;
import static decorps.eventprocessor.utils.BaseUtils.printOutBytesAsHexa;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.SysexMessage;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;

import decorps.eventprocessor.EventProcessorShortMessage;
import decorps.eventprocessor.EventProcessorShortMessageComposite;
import decorps.eventprocessor.EventProcessorTest;
import decorps.eventprocessor.TestingDSITetraInfo;

public class DsiTetraMapTest {

	private static final String CHANNEL_ONE = "0000";
	private static final String PROGAM_ONE = "000 0001";
	final SysexMessage sampleMsg = EventProcessorTest
			.getSampleProgramDataDumpSysexMessage();
	final DsiTetraMap cut = new DsiTetraMap();
	EventProcessorShortMessageComposite result = cut.convert(sampleMsg);

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
		assertThat(byteToBinary((byte) oneByte), is(representation));
	}

	void checkStringToByte(String representation, int oneByte) {
		assertThat(binaryToByte(representation), is((byte) oneByte));
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

		printOutBytesAsHexa(next8BytePackets);
		printOutBinaryMessage(next8BytePackets);
		for (int i = 0; i < 8; i++)
			assertEquals("row " + (i + 1) + " is "
					+ byteToBinary(MSB(next8BytePackets[i * 8])), 0,
					MSB(next8BytePackets[i * 8]));
	}

	private byte MSB(byte currentByte) {
		String byteAsBinary = byteToBinary(currentByte);
		return Byte.parseByte(byteAsBinary.substring(0, 4), 2);
	}

	@Test
	public void Hexa18Is11000() throws Exception {
		assertEquals(byteToHex(binaryToByte("0001 1000")), "18");
	}

	@Test
	public void canRecognizeTetraFromInfo() throws Exception {
		assertTrue(DsiTetraMap.isTetra(new TestingDSITetraInfo()));
	}

	@Test
	public void canRecognizeAProgramChangeForTetra() throws Exception {
		EventProcessorShortMessage midiMessage = EventProcessorShortMessage
				.build("1100 " + CHANNEL_ONE, "0" + PROGAM_ONE, null);
		Assert.assertTrue(midiMessage.is(TetraParameters.ProgramChange));
	}
}
