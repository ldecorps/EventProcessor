package decorps.eventprocessor.rules;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.EventProcessorMidiMessage;
import decorps.eventprocessor.EventProcessorShortMessage;

public class Transpose implements Rule, ToTetra, FromTetra {
	final public int transposeBy;

	public Transpose(int transposeBy) {
		this.transposeBy = transposeBy;
	}

	@Override
	public EventProcessorMidiMessage transform(
			EventProcessorMidiMessage eventProvessorShortMessage) {
		ShortMessage incomingMessage = ((EventProcessorShortMessage) eventProvessorShortMessage).shortMessage;

		int command = incomingMessage.getCommand();
		int channel = incomingMessage.getChannel();
		int data1 = incomingMessage.getData1() + transposeBy;
		int data2 = incomingMessage.getData2();

		ShortMessage outgoingMessage;
		try {
			outgoingMessage = new ShortMessage(command, channel, data1, data2);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}

		EventProcessorMidiMessage result = EventProcessorShortMessage
				.build(outgoingMessage);

		return result;
	}
}
