package decorps.eventprocessor.snipets;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.LinkFactory;

public class SetLed {

	public static void main(String[] args) throws MidiUnavailableException,
			InvalidMidiDataException, InterruptedException {
		Receiver lividReceiver = null;
		for (Info info : LinkFactory.getMidiDeviceInfo()) {
			if (!info.getName().equals("Controls"))
				continue;
			MidiDevice tetra = MidiSystem.getMidiDevice(info);
			if (tetra.getMaxReceivers() == 0)
				continue;
			lividReceiver = tetra.getReceiver();
		}

		SysexMessage sysexMessage = new SysexMessage();
		for (byte i = 0; i < 0x1F; i++) {
			sysexMessage.setMessage(new byte[] { (byte) 0xF0, 0x0, 0x01, 0x61,
					0x04, 0x04, i, i, i, i, i, i, i, i, (byte) 0xF7 }, 15);
			Thread.sleep(5);
		}
		lividReceiver.send(sysexMessage, -1);
	}
}
