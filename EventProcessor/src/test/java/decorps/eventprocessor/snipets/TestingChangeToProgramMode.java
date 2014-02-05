package decorps.eventprocessor.snipets;

import javax.sound.midi.InvalidMidiDataException;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;

public class TestingChangeToProgramMode {

	public static void main(String[] args) throws InvalidMidiDataException {
		EventProcessor eventProcessor = EventProcessor.getInstance();
		eventProcessor.fromTetraToLivid.receiver.send(
				DsiMessageFactory.buildMode_Change__ProgramChange(), -1);
	}

}
