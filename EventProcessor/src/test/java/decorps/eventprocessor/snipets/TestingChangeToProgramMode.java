package decorps.eventprocessor.snipets;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class TestingChangeToProgramMode {

	public static void main(String[] args) throws InvalidMidiDataException {
		EventProcessor eventProcessor = new EventProcessor();
		SysexMessage sysex;
		sysex = new SysexMessage();
		sysex.setMessage(DsiTetraMap.Mode_Change__ProgramChange,
				DsiTetraMap.Mode_Change__ProgramChange.length);
		eventProcessor.fromTetraToLivid.receiver.send(sysex, -1);
	}

}
