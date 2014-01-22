package decorps.eventprocessor.vendors.livid;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class LividCodeEventProcessorCCShortMessage extends
		EventProcessorShortMessage {
	@Override
	public String toString() {
		return "LividCodeEventProcessorCCShortMessage [type=" + type
				+ ", value=" + value + ", channel=" + channel
				+ ", abstractProgramParameter=" + abstractProgramParameter
				+ "]";
	}

	final byte type;
	final byte value;
	final byte channel;
	public final AbstractProgramParameter abstractProgramParameter;

	public LividCodeEventProcessorCCShortMessage(
			AbstractProgramParameter abstractProgramParameter, byte channel,
			byte type, byte value) {
		super(buildLividCodeShortMessage(type, value, channel));
		this.type = type;
		this.value = value;
		this.channel = channel;
		this.abstractProgramParameter = abstractProgramParameter;
	}

	private static ShortMessage buildLividCodeShortMessage(byte type,
			byte value, byte channel) {
		ShortMessage result;
		try {
			result = new ShortMessage(ShortMessage.CONTROL_CHANGE, channel,
					type, value);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
		return result;
	}
}
