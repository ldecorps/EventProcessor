package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.utils.BaseUtils;

public class EventProcessorSysexMessage extends EventProcessorMidiMessage {

	final public SysexMessage sysexMessage;

	public static EventProcessorSysexMessage build(SysexMessage sysexMessage) {
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
			sysexMessage = new SysexMessage(data, data.length);
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

}