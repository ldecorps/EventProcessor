package decorps.eventprocessor.vendors.livid;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class LividCodeEventProcessorCCShortMessage extends
		EventProcessorShortMessage {
	@Override
	public String toString() {
		return "LividCodeEventProcessorCCShortMessage [type=" + type
				+ ", value=" + value + ", channel=" + channel
				+ ", programParameter=" + programParameter + "]";
	}

	final byte type;
	final byte value;
	final byte channel;
	public final ProgramParameter programParameter;

	public LividCodeEventProcessorCCShortMessage(
			ProgramParameter programParameter, Controller controller,
			byte rebasedValue) {
		super(buildLividCodeShortMessage(programParameter, controller,
				rebasedValue));
		this.type = (byte) controller.getCCNumber();
		this.value = rebasedValue;
		this.channel = (byte) ((byte) BankLayout.CurrentBank.bankNumber - 1);
		this.programParameter = programParameter;
	}

	private static ShortMessage buildLividCodeShortMessage(
			ProgramParameter programParameter, Controller controller, byte value) {
		ShortMessage result;
		try {
			final int shortMessageType = Encoder.class
					.isAssignableFrom(controller.getClass()) ? ShortMessage.CONTROL_CHANGE
					: ShortMessage.NOTE_ON;
			result = new ShortMessage(shortMessageType,
					(byte) ((byte) BankLayout.CurrentBank.bankNumber - 1),
					controller.getCCNumber(), value);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(
					"Invalid Midi Data Exception when trying to build CC for "
							+ programParameter, e);
		}
		return result;
	}
}
