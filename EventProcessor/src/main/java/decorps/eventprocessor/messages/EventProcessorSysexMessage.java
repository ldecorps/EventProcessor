package decorps.eventprocessor.messages;

import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.EventProcessorException;

public class EventProcessorSysexMessage extends EventProcessorMidiMessage {

	final public SysexMessage sysexMessage;

	protected static EventProcessorSysexMessage buildSysexMessage(
			SysexMessage sysexMessage) {
		EventProcessorSysexMessage result = new EventProcessorSysexMessage(
				sysexMessage.getMessage());
		return result;
	}

	protected EventProcessorSysexMessage(byte[] data) {
		super(data);
		try {
			sysexMessage = new SysexMessage();
			sysexMessage.setMessage(data, data.length);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException("Could not build sysex "
					+ bytesToHexa(data), e);
		}
	}

	@Override
	public Object clone() {
		throw new EventProcessorException("Not Implemented Yet");
	}

	public static EventProcessorSysexMessage build(byte... bytes) {
		return new EventProcessorSysexMessage(bytes);
	}

	@Override
	protected MidiMessage getMidiMessage() {
		return sysexMessage;
	}
}
