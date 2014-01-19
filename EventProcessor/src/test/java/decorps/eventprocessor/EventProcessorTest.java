package decorps.eventprocessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.SetLedAndLedRingIndicatorsRule;
import decorps.eventprocessor.rules.Transpose;
import decorps.eventprocessor.utils.DumpReceiver;
import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;
import decorps.eventprocessor.vendors.dsi.TetraParameter;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class EventProcessorTest {

	EventProcessor cut;

	@Before
	public void buildEventProcessor() {
		cut = EventProcessor.build();
	}

	@After
	public void close() {
		cut.close();
	}

	@Test
	public void newEventProcessor_shouldHaveAReceiver() {
		assertThat(EventProcessor.build().fromTetraToLivid.receiver,
				notNullValue());
	}

	@Test
	public void newEventProcessor_shouldHaveATransmitter() throws Exception {
		assertThat(EventProcessor.build().fromTetraToLivid.transmitter,
				notNullValue());
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
		return cut.fromTetraToLivid.receiver.getFirstSentMidiMessage();
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
		SysexMessage myMsg = DsiTetraMapTest.sampleProgramDataDump;
		long timeStamp = -1;
		cut.getDefaultRemoteReceiver().send(myMsg, timeStamp);
	}

	private List<EventProcessorMidiMessage> getSentMessages() {
		return cut.fromTetraToLivid.receiver.getSentMidiMessages();
	}

	@Test
	public void canConvertSysExInSeveralShortMessages() throws Exception {
		sendSysEx();
		assertThat(getSentMessages(), hasSize(greaterThan(0)));
	}

	@Test
	public void willDefaultReceiverToDump_ifLividReceiverIsNotAvailable()
			throws Exception {
		EventProcessor cut = EventProcessor.build();
		if (!LinkFactory.isLividCodev2PluggedIn())
			assertThat(cut.fromTetraToLivid.receiver.getRawReceiver(),
					instanceOf(DumpReceiver.class));
		else {
			assertThat(cut.fromTetraToLivid.receiver.getRawReceiver(),
					not(instanceOf(DumpReceiver.class)));
		}
	}

	@Test
	public void registersRule_ProgramEditBufferDataDumpRequest_to_ProgramChange()
			throws Exception {
		cut.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, cut.fromTetraToTetra);
		MidiMessage sampleProgramChange = DsiTetraMapTest.sendProgramChange();
		assertThat(cut.fromTetraToTetra.receiver.getSentMidiMessages(), empty());

		cut.fromTetraToTetra.receiver.send(sampleProgramChange, -1);

		assertThat(cut.fromTetraToTetra.receiver.getSentMidiMessages(),
				not(empty()));
		assertArrayEquals(
				ProgramEditBufferDumpRequest.programEditBufferDumpRequest
						.getMessage(),
				cut.fromTetraToTetra.receiver.getSentMidiMessages().get(0)
						.getMessage());
	}

	@Test
	public void registerRule_SendAllLedInfosToLividCode_To_ProgramDataDump()
			throws Exception {
		cut.registerAction(new SetLedAndLedRingIndicatorsRule(),
				TetraParameter.ProgramEditBufferDataDump, cut.fromTetraToLivid);
		MidiMessage sampleEditBufferProgramDataDump = DsiTetraMapTest.sampleEditbyfferProgramDataDump;

		cut.fromTetraToLivid.receiver.send(sampleEditBufferProgramDataDump, -1);

		for (EventProcessorMidiMessage message : ((EventProcessorMidiMessageComposite) cut.fromTetraToLivid.receiver
				.getSentMidiMessages().get(0)).eventProcessorMidiMessages) {
			if (Arrays.equals(message.getMessage(), LividMessageFactory
					.buildSet_all_LED_indicators().getMessage())
					|| Arrays.equals(message.getMessage(), LividMessageFactory
							.buildSet_LED_Ring_indicators().getMessage()))
				fail("Message should contain some non zero data: mapping is not working");
		}
	}
}
