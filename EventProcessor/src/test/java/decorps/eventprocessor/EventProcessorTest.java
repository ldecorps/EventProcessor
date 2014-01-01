package decorps.eventprocessor;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.rules.Transpose;

public class EventProcessorTest {

	EventProcessor cut = EventProcessor.build();

	@Before
	public void open() throws MidiUnavailableException {
		cut.open();
	}

	@Test
	public void newEventProcessor_shouldHaveAReceiver() {
		assertThat(EventProcessor.build().link.receiver, notNullValue());
	}

	@Test
	public void newEventProcessor_shouldHaveATransmitter() throws Exception {
		assertThat(EventProcessor.build().link.transmitter, notNullValue());
	}

	@Test
	public void newEventProcessor_HasNotSetTheReceiverInTheTransmitter()
			throws Exception {
		assertThat(EventProcessor.build().getDefaultRemoteReceiver(),
				nullValue());
	}

	@Test
	public void eventProcess_IsAMidiDevice() throws Exception {
		assertThat(cut, isA(MidiDevice.class));
	}

	@Test
	public void onceOpened_theReceiverIsSetInTheTransmitter() throws Exception {
		assertThat(cut.getDefaultRemoteReceiver(),
				sameInstance(cut.getReceiver()));
	}

	private ShortMessage sendMiddleC() throws InvalidMidiDataException {
		ShortMessage myMsg = new ShortMessage();
		myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
		long timeStamp = -1;
		cut.getDefaultRemoteReceiver().send(myMsg, timeStamp);
		return myMsg;
	}

	private EventProcessorShortMessage getSentMessage() {
		return ((RulesAwareReceiverWrapper) cut.getReceiver())
				.getSentMidiMessage();
	}

	@Test
	public void midiMessageIsPassedAlong() throws Exception {
		ShortMessage myMsg = sendMiddleC();
		assertThat(getSentMessage().getData1(), is(myMsg.getData1()));
	}

	@Test
	public void canTranspose_Plus3() throws Exception {
		cut.registerDefaultRule(new Transpose(3));
		ShortMessage middleC = sendMiddleC();
		assertThat(getSentMessage().getData1(), is(middleC.getData1() + 3));
	}

	private void sendSysEx() throws InvalidMidiDataException,
			FileNotFoundException, IOException {
		SysexMessage myMsg = getSampleProgramDataDumpSysexMessage();
		long timeStamp = -1;
		cut.getDefaultRemoteReceiver().send(myMsg, timeStamp);
	}

	public static SysexMessage getSampleProgramDataDumpSysexMessage() {
		SysexMessage myMsg = new SysexMessage();
		try {
			byte[] bytes;
			bytes = IOUtils.toByteArray(new FileInputStream(new File(
					"src/test/resources/oneProgram")));
			int length = bytes.length;
			myMsg.setMessage(bytes, length);
		} catch (IOException | InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
		return myMsg;
	}

	private Collection<? extends Object> getSentMessages() {
		return ((RulesAwareReceiverWrapper) cut.getReceiver())
				.getSentMidiMessages();
	}

	@Test
	public void canConvertSysExInSeveralShortMessages() throws Exception {
		sendSysEx();
		assertThat(getSentMessages(), hasSize(greaterThan(0)));
	}

	@Test
	public void onlySupportsOneReceiverAndOneTransmitter() throws Exception {
		assertThat(cut.getMaxReceivers(), is(1));
		assertThat(cut.getMaxTransmitters(), is(1));
	}

	@Test
	public void willDefaultReceiverToDummy_ifTetrasReceiverIsNotAvailable()
			throws Exception {
		EventProcessor cut = EventProcessor.build();
		if (!EventProcessor.isTetraPluggedIn())
			assertThat(cut.link.receiver.getRawReceiver(),
					instanceOf(DummyReceiver.class));
		else {
			assertThat(cut.link.receiver.getRawReceiver(),
					not(instanceOf(DummyReceiver.class)));
		}
	}
}
