package decorps.eventprocessor;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;

public class ProgramComboSwitcher {

	public static void main(String[] args) throws MidiUnavailableException,
			InvalidMidiDataException, InterruptedException {
		Receiver tetraReceiver = null;
		for (Info info : MidiSystem.getMidiDeviceInfo()) {
			if (!info.getName().equals("DSI Tetra"))
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
			sysexMessage.setMessage(new byte[] { (byte) 0xF0, (byte) 0x01,
					(byte) 0x26, switchTo, (byte) 0xF7 }, 5);
			tetraReceiver.send(sysexMessage, -1);
			Thread.sleep(1000);
		}
	}
}
