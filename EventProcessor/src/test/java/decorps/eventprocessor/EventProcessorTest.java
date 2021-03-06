package decorps.eventprocessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.rules.LividEncoderOrButtonValue_NewValue_SendToTetra;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.RelativeEncoderChangeEchoesNewLEDRingValue;
import decorps.eventprocessor.rules.Rule;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.rules.Transpose;
import decorps.eventprocessor.utils.DumpReceiver;
import decorps.eventprocessor.vendors.dsi.DsiTetraMapTest;
import decorps.eventprocessor.vendors.dsi.MessageType;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.livid.Encoder;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class EventProcessorTest {

	EventProcessor cut;

	@Before
	public void buildEventProcessor() {
		cut = getInstanceWithoutActions();
	}

	public static EventProcessor getInstanceWithoutActions() {
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;
		EventProcessor result = EventProcessor.build();
		result.actions.clear();
		return result;
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
		Assert.assertThat(getSentMessage().getData1(), is(myMsg.getData1()));
	}

	@Test
	public void canTranspose_Plus3() throws Exception {
		cut.registerDefaultRule(new Transpose(3));
		ShortMessage middleC = sendMiddleC();
		Assert.assertThat(getSentMessage().getData1(),
				is(middleC.getData1() + 3));
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
				MessageType.ProgramChange, cut.fromTetraToTetra);
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
	public void turningRelativeEncoder_sendsCcBackToLivid() throws Exception {
		MapRepository.initialise();
		registerRuleThatUpdatesValue();
		cut.registerAction(new RelativeEncoderChangeEchoesNewLEDRingValue(),
				MessageType.RELATIVE_ONLY, cut.fromLividToLivid);
		EventProcessorMidiMessage lividRelativeEncoderTurned = buildLividEncoderForFineFreqTurnedClockwise();
		int initialParameterValue = ControllerRepository
				.getControllerForLividShortMessage(lividRelativeEncoderTurned)
				.getRebasedValue();

		cut.fromLividToLivid.receiver.send(lividRelativeEncoderTurned);
		assertThat(cut.fromLividToLivid.receiver.getSentMidiMessages(),
				hasSize(2));
		assertThat(cut.fromLividToLivid.receiver.getSentMidiMessages().get(1)
				.getAsShortMessage().getData2(),
				greaterThan(initialParameterValue));
	}

	private void registerRuleThatUpdatesValue() {
		cut.registerAction(
				new LividEncoderOrButtonValue_NewValue_SendToTetra(),
				MessageType.ANY_NOTE_OR_CC, cut.fromLividToLivid);
	}

	private EventProcessorMidiMessage buildLividEncoderForFineFreqTurnedClockwise() {
		final Encoder osc1FineFreqController = ControllerRepository
				.getEncoderForParameterClass(Osc1FineFreq.class);
		final int ccNumberOfaRelativeEncoder = osc1FineFreqController
				.getCCOrNoteNumber();
		EventProcessorMidiMessage result = EventProcessorShortMessage
				.buildLividIncrementCC(ccNumberOfaRelativeEncoder);
		return result;
	}

	@Test
	public void registerRule_SendAllLedInfosToLividCode_To_ProgramDataDump()
			throws Exception {
		registering(new SetEncodersAndLedIndicatorsRule());

		injectingProgramDataDump();

		for (EventProcessorMidiMessage message : getSentComposite()) {
			if (Arrays.equals(message.getMessage(), LividMessageFactory
					.buildSet_all_LED_indicators().getMessage())
					&& Arrays.equals(message.getMessage(), LividMessageFactory
							.buildSet_LED_Ring_indicators().getMessage()))
				fail("Message should contain some non zero data: mapping is not working");
		}
	}

	private List<EventProcessorMidiMessage> getSentComposite() {
		final List<EventProcessorMidiMessage> sentMidiMessages = cut.fromTetraToLivid.receiver
				.getSentMidiMessages();
		return sentMidiMessages;
	}

	private void registering(Rule rule) {
		cut.registerAction(rule, MessageType.ProgramEditBufferDataDump,
				cut.fromTetraToLivid);
	}

	private void injectingProgramDataDump() {
		cut.fromTetraToLivid.receiver.send(
				DsiTetraMapTest.sampleEditbufferProgramDataDump, -1);
	}

	public List<EventProcessorMidiMessage> getInnerCompositeCcs() {
		for (EventProcessorMidiMessage message : getSentComposite()) {
			if (!EventProcessorMidiMessageComposite.class
					.isAssignableFrom(message.getClass()))
				continue;
			return message.getAsComposite().getMessages();
		}
		throw new EventProcessorException("Did not find any messages");
	}

}
