package decorps.eventprocessor.vendors.livid;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.maps.DefaultMap;

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
	final DefaultMap map;
	public final AbstractProgramParameter abstractProgramParameter;

	public LividCodeEventProcessorCCShortMessage(
			AbstractProgramParameter abstractProgramParameter, DefaultMap map,
			byte type, byte value) {
		super(buildLividCodeShortMessage(abstractProgramParameter, type, value,
				map));
		this.type = type;
		this.value = value;
		this.channel = map.bank;
		this.abstractProgramParameter = abstractProgramParameter;
		this.map = map;
	}

	private static ShortMessage buildLividCodeShortMessage(
			AbstractProgramParameter abstractProgramParameter, byte type,
			byte value, DefaultMap map) {
		ShortMessage result;
		try {
			final int shortMessageType = EncoderMap.class.isAssignableFrom(map
					.getClass()) ? ShortMessage.CONTROL_CHANGE
					: ShortMessage.NOTE_ON;
			result = new ShortMessage(shortMessageType, map.bank, type, value);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(
					"Invalid Midi Data Exception when trying to build CC for "
							+ abstractProgramParameter, e);
		}
		return result;
	}
}
