package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;
import static decorps.eventprocessor.utils.BaseUtils.byteToBinary;
import static decorps.eventprocessor.utils.BaseUtils.byteToHexa;
import static decorps.eventprocessor.utils.BaseUtils.printOutBytesAsHexa;
import static decorps.eventprocessor.utils.BaseUtils.printOutHexaMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class DsiTetraMapTest {

	private static final String CHANNEL_ONE = "0000";
	private static final String PROGAM_ONE = "000 0001";
	public static final SysexMessage sampleProgramDataDump = getSampleProgramDataDumpSysexMessage();
	public static final SysexMessage sampleEditbyfferProgramDataDump = getSampleEditBufferDataDumpSysexMessage();
	final DsiTetraMap cut = new DsiTetraMap();
	EventProcessorMidiMessage result = cut.convert(sampleProgramDataDump);
	private ProgramDataDump programDataDump;
	private Layer currentLayer;

	@Test
	public void convertStringToByte() throws Exception {
		checkStringToByte("0000 0000", 0);
		checkStringToByte("0000 0001", 1);
		checkStringToByte("0000 1111", 15);
		checkStringToByte("0001 0000", 16);
		checkStringToByte("0010 0000", 32);
		checkStringToByte("0100 0000", 64);
		checkStringToByte("0111 1110", 126);
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
		checkBinaryToString(126, "0111 1110");
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
		printOutHexaMessage(sampleProgramDataDump);
		assertTrue(cut.isValidTetraProgramDump());
	}

	@Test
	public void hasPackedDataFormat() throws Exception {
		byte[] sysexMessage = sampleProgramDataDump.getMessage();
		assertTrue(DsiTetraMap.isProgramDataDump(sysexMessage));
		DsiTetraMap.isProgramDataDump(sysexMessage);
		programDataDump = ProgramDataDump.buildProgramDump(sysexMessage);
		assertEquals(programDataDump.bankNumber, 3);
		assertEquals(programDataDump.programNumber, 24);
		assertEquals(programDataDump.programParameterData.Name, "PulseOrgan");
		currentLayer = programDataDump.programParameterData.currentLayer;
		assertData(0x18, currentLayer.oscillator1Frequency);
		assertData(0x33, currentLayer.oscillator1FineTune);
		assertData(0x0e, currentLayer.oscillator1Shape);

	}

	public void assertData(int expected, AbstractProgramParameter actual) {
		try {
			assertEquals(expected, actual.data);
		} catch (AssertionError e) {
			printOutBytesAsHexa(programDataDump.unpackedMessages);
			throw e;
		}
	}

	public void endsWithEndOfExclusive(byte[] sysexMessage) {
		assertTrue(cut.isEndOfExclusive(sysexMessage[sysexMessage.length - 1]));
	}

	public void startsWithSystemExclusive(byte[] sysexMessage) {
		assertTrue(cut.isSystemExclusive(sysexMessage[0]));
	}

	@Test
	public void Hexa18Is11000() throws Exception {
		assertEquals(byteToHexa(binaryToByte("0001 1000")), "18");
	}

	@Test
	public void canRecognizeTetraFromInfo() throws Exception {
		assertTrue(DsiTetraMap.isTetra(new TestingDSITetraInfo()));
	}

	@Test
	public void canRecognizeAProgramChangeForTetra() throws Exception {
		EventProcessorMidiMessage midiMessage = EventProcessorShortMessage
				.build("1100 " + CHANNEL_ONE, "0" + PROGAM_ONE, null);
		Assert.assertTrue(midiMessage.is(TetraParameter.ProgramChange));
	}

	@Test
	public void various() {
		assertThat(new byte[] { (byte) 0xF0, (byte) 0x01, (byte) 0x26,
				(byte) 0x31, (byte) 0xF7 }, is(DsiMessageFactory
				.buildMode_Change__ComboChange().getMessage()));
	}

	private static SysexMessage getSampleProgramDataDumpSysexMessage() {
		return createSysexFromFile("src/test/resources/programDataDump");
	}

	private static SysexMessage getSampleEditBufferDataDumpSysexMessage() {
		return createSysexFromFile("src/test/resources/editBufferProgramDataDump");
	}

	public static SysexMessage createSysexFromFile(String file) {
		SysexMessage myMsg = new SysexMessage();
		try {
			byte[] bytes;
			bytes = IOUtils.toByteArray(new FileInputStream(new File(file)));
			int length = bytes.length;
			myMsg.setMessage(bytes, length);
		} catch (IOException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
		return myMsg;
	}

	public static MidiMessage sendProgramChange() {
		EventProcessorMidiMessage buildShortMessage = EventProcessorShortMessage
				.buildShortMessage(ShortMessage.PROGRAM_CHANGE, 0x00000001, 0);
		assertTrue(DsiTetraMap.isProgramChange(buildShortMessage));
		return buildShortMessage;
	}
}
