package decorps.eventprocessor.snipets;

import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.LinkFactory;

public class ProgramComboSwitcher {

	public static void main(String[] args) throws MidiUnavailableException,
			InterruptedException, InvalidMidiDataException {
		Receiver tetraReceiver = null;
		for (Info info : LinkFactory.midiDeviceInfos) {
			if (!info.getName().equals("DSI Tetra - DSI Tetra"))
				continue;
			MidiDevice tetra = MidiSystem.getMidiDevice(info);
			if (tetra.getMaxReceivers() == 0)
				continue;
			tetraReceiver = tetra.getReceiver();
		}
		byte progChange = (byte) 0x30;
		byte comboChange = (byte) 0x31;
		byte switchTo = progChange;
		while (true) {
			SysexMessage sysexMessage = new SysexMessage();
			switchTo = switchTo == progChange ? comboChange : progChange;
			try {
				sysexMessage.setMessage(0xF0, new byte[] { (byte) 0x01,
						(byte) 0x26, switchTo, (byte) (0xF7 & 0xFF) }, 4);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			tetraReceiver.send(sysexMessage, -1);
			System.out.println(Arrays.asList(sysexMessage.getData()));
			Thread.sleep(1000);
		}
	}
}
