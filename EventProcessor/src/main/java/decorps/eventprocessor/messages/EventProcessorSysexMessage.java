package decorps.eventprocessor.messages;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.utils.BaseUtils;

public class EventProcessorSysexMessage extends EventProcessorMidiMessage {

	final public SysexMessage sysexMessage;

	protected static EventProcessorSysexMessage buildSysexMessage(
			SysexMessage sysexMessage) {
		return new EventProcessorSysexMessage(sysexMessage.getData());
	}

	public static EventProcessorSysexMessage build(String... bytes) {
		List<Byte> listOfBytes = new ArrayList<Byte>();
		for (String currentByte : bytes) {
			listOfBytes.add(BaseUtils.binaryToByte(currentByte));
		}
		byte[] result = new byte[listOfBytes.size()];
		for (int i = 0; i < listOfBytes.size(); i++) {
			result[i] = listOfBytes.get(i).byteValue();
		}
		return new EventProcessorSysexMessage(result);
	}

	protected EventProcessorSysexMessage(byte[] data) {
		super(data);
		try {
			sysexMessage = new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE,
					data, data.length);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
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
