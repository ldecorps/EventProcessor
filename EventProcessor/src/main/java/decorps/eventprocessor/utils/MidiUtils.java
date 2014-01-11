package decorps.eventprocessor.utils;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class MidiUtils {
	public List<String> listMidiDevices() throws MidiUnavailableException {
		final List<String> synthInfos = new ArrayList<String>();

		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			String description = "";
			final MidiDevice device = MidiSystem.getMidiDevice(info);
			if (device instanceof Synthesizer) {
				description = "Synthesizer: " + info.getName();
			}
			if (device instanceof Sequencer) {
				description = "Sequencer: " + info.getName();
			} else {
				description = info.getName() + "(" + info.getDescription()
						+ ")";
			}
			boolean bAllowsInput = (device.getMaxTransmitters() != 0);
			boolean bAllowsOutput = (device.getMaxReceivers() != 0);
			if (bAllowsInput)
				description += " for input (has transmitters)";
			if (bAllowsOutput)
				description += " for output (has receivers)";
			synthInfos.add(description);
		}
		return synthInfos;
	}

	public static byte[] buildSysexMessage(int size, int... bytes) {
		byte[] result = new byte[size];
		result[0] = (byte) 0xF0;
		result[result.length - 1] = (byte) 0xF7;
		for (int i = 0; i < bytes.length; i++)
			result[i + 1] = (byte) bytes[i];
		return result;
	}

	public static byte[] addToArrayAfter(byte[] array, int addAfter,
			int... bytes) {
		for (int i = 0; i < bytes.length; i++)
			array[i + addAfter + 1] = (byte) bytes[i];
		return array;
	}
}
