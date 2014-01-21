package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.byteToBinary;
import static decorps.eventprocessor.utils.BaseUtils.byteToHexa;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.System_Exclusive;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.utils.BaseUtils;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;

public class DsiTetraMap {

	private static final String VENDOR = "Dave Smith Instruments";
	private static final String NAME = "DSI Tetra";
	private static final String MMJ_NAME = "DSI Tetra - DSI Tetra";

	private byte[] messageAsBytes;

	public EventProcessorMidiMessage convert(SysexMessage message) {
		List<EventProcessorMidiMessage> listOfMessages = new ArrayList<EventProcessorMidiMessage>();
		messageAsBytes = message.getMessage();
		if (!isValidTetraProgramDump())
			return null;
		for (int i = 5; i < messageAsBytes.length; i++) {
			listOfMessages.add(doByte(messageAsBytes[i]));
		}
		EventProcessorMidiMessage eventProcessorShortMessageComposite = EventProcessorMidiMessageComposite
				.buildComposite(listOfMessages
						.toArray(new EventProcessorMidiMessage[] {}));
		return eventProcessorShortMessageComposite;
	}

	boolean isValidTetraProgramDump() {
		return isProgramDataDump(messageAsBytes);
	}

	private EventProcessorMidiMessage doByte(byte currentByte) {
		if (isEndOfExclusive(currentByte))
			return null;
		ShortMessage shortMessage = new ShortMessage();
		try {
			shortMessage.setMessage(ShortMessage.CONTROL_CHANGE, 0,
					currentByte, 1);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(
					"Could not sent control change for byte: "
							+ byteToHexa(currentByte) + " "
							+ byteToBinary(currentByte), e);
		}
		EventProcessorShortMessage eventProcessorShortMessage = (EventProcessorShortMessage) EventProcessorMidiMessage
				.build(shortMessage);
		return eventProcessorShortMessage;
	}

	public boolean isEndOfExclusive(byte currentByte) {
		return -9 == currentByte;
	}

	public static boolean isTetra(Info info) {
		return (NAME.equals(info.getName()) && VENDOR.equals(info.getVendor()))
				|| info.getName().equals("USB Uno MIDI Interface")
				|| info.getName().equals(MMJ_NAME);
	}

	public static boolean isProgramChange(String status, String second,
			String third) {
		return null == third && status.startsWith("1100");
	}

	public static byte buildBankNumber(byte bankNumber) {
		return bankNumber;
	}

	public static byte buildProgramNumber(byte programNumber) {
		return programNumber;
	}

	public boolean isSystemExclusive(byte b) {
		return System_Exclusive == b;
	}

	public static boolean isProgramDataDump(byte[] sysexMessage) {
		return DsiMessageFactory.isProgramDataDump(sysexMessage);
	}

	public static boolean isProgramEditBufferDataDump(byte[] sysexMessage) {
		return DsiMessageFactory.isProgramEditBufferDataDump(sysexMessage);
	}

	public static boolean isProgramChange(EventProcessorMidiMessage shortMessage) {
		return isProgramChange(
				BaseUtils.byteToBinary((byte) shortMessage.getStatus()), null,
				null);
	}
}
