package decorps.eventprocessor;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.rules.ProgramDumpRequestRule;
import decorps.eventprocessor.rules.Transpose;
import decorps.eventprocessor.utils.DumpReceiver;

public class EventProcessorTest {

	EventProcessor cut = EventProcessor.build();

	@Test
	public void newEventProcessor_shouldHaveAReceiver() {
		assertThat(EventProcessor.build().fromTetra.receiver, notNullValue());
	}

	@Test
	public void newEventProcessor_shouldHaveATransmitter() throws Exception {
		assertThat(EventProcessor.build().fromTetra.transmitter, notNullValue());
	}

	@Test
	public void newEventProcessor_HasSetTheReceiverInTheTransmitter()
			throws Exception {
		assertThat(EventProcessor.build().getDefaultRemoteReceiver(),
				notNullValue());
	}

	private ShortMessage sendMiddleC() throws InvalidMidiDataException {
		ShortMessage myMsg = new ShortMessage();
		myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
		long timeStamp = -1;
		cut.getDefaultRemoteReceiver().send(myMsg, timeStamp);
		return myMsg;
	}

	private EventProcessorShortMessage getSentMessage() {
		return cut.fromTetra.receiver.getSentMidiMessage();
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

	private List<EventProcessorShortMessage> getSentMessages() {
		return cut.fromTetra.receiver.getSentMidiMessages();
	}

	@Test
	public void canConvertSysExInSeveralShortMessages() throws Exception {
		sendSysEx();
		assertThat(getSentMessages(), hasSize(greaterThan(0)));
	}

	@Test
	public void willDefaultReceiverToDump_ifTetrasReceiverIsNotAvailable()
			throws Exception {
		EventProcessor cut = EventProcessor.build();
		if (!EventProcessor.isTetraPluggedIn())
			assertThat(cut.fromTetra.receiver.getRawReceiver(),
					instanceOf(DumpReceiver.class));
		else {
			assertThat(cut.fromTetra.receiver.getRawReceiver(),
					not(instanceOf(DumpReceiver.class)));
		}
	}

	@Test
	public void canRegisterARuleToAProgramChange() throws Exception {
		cut.registerAction(new ProgramDumpRequestRule(),
				TetraParameter.ProgramChange, cut.fromTetra);
	}
}
