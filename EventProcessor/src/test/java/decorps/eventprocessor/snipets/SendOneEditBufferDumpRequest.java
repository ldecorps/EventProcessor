package decorps.eventprocessor.snipets;

import static decorps.eventprocessor.utils.BaseUtils.decodeMessage;
import static decorps.eventprocessor.vendors.dsi.DsiTetraMap.DSI_ID;
import static decorps.eventprocessor.vendors.dsi.DsiTetraMap.RequestProgramEditBufferTransmit;
import static decorps.eventprocessor.vendors.dsi.DsiTetraMap.Tetra_ID;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.LinkFactory;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class SendOneEditBufferDumpRequest {

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
		byte[] EditBufferDumpRequest = new byte[] {
				DsiTetraMap.System_Exclusive, DSI_ID, Tetra_ID,
				RequestProgramEditBufferTransmit, DsiTetraMap.End_Of_Exclusive };
		while (true) {
			SysexMessage sysexMessage = new SysexMessage();
			try {
				sysexMessage.setMessage(EditBufferDumpRequest,
						EditBufferDumpRequest.length);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			System.out.println("Sending " + decodeMessage(sysexMessage));
			tetraReceiver.send(sysexMessage, -1);
			Thread.sleep(10000);
		}
	}
}
