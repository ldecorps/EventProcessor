package decorps.eventprocessor;

import java.util.Set;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.akai.AkaiMap;
import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.utils.DumpReceiver;

public class LinkFactory {

	public final Set<Action> actions;

	LinkFactory(Set<Action> actions) {
		this.actions = actions;
	}

	public Link buildFromTetraIfPluggedInToLocal() {
		return build(tryToGetTetraOrDefaultToDumpReceiver(),
				getDefaultDummyLocalTansmitter());
	}

	Link build(Receiver receiver, Transmitter transmitter) {
		return Link.build(RulesAwareReceiverWrapper.build(receiver, actions),
				transmitter);
	}

	Receiver tryToGetTetraOrDefaultToDumpReceiver() {
		if (isTetraPluggedIn())
			return getTetraReceiver();
		System.out.println("Connecting to DumpReceiver");
		return new DumpReceiver(System.out);
	}

	Transmitter getDefaultDummyLocalTansmitter() {
		return new DummyTransmitter();
	}

	private Receiver getTetraReceiver() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (!DsiTetraMap.isTetra(info))
				continue;
			MidiDevice device;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxReceivers() == 0)
					continue;
				device.open();
				System.out.println("connected to " + info.getName()
						+ " receiver");
				return device.getReceiver();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		throw new EventProcessorException("Could not get a receiver from Tetra");
	}

	public static boolean isTetraPluggedIn() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (DsiTetraMap.isTetra(info))
				return true;
		}
		return false;
	}

	public static boolean isAkaiLpk25PluggedIn() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (AkaiMap.isLpk25(info))
				return true;
		}
		return false;
	}

	public Link buildFromLocalToTetraIfPluggedIn() {
		return build(getDefaultDumpLocalReceiver(),
				tryToGetTetraOrDefaultToDummyTransmitter());

	}

	private Transmitter tryToGetTetraOrDefaultToDummyTransmitter() {
		if (isTetraPluggedIn())
			return getTetraTransmitter();
		return new DummyTransmitter();
	}

	private Transmitter getTetraTransmitter() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (!DsiTetraMap.isTetra(info))
				continue;
			MidiDevice device;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxTransmitters() == 0)
					continue;
				System.out.println("connected to " + info.getName()
						+ " transmitter");
				return device.getTransmitter();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		throw new EventProcessorException(
				"Could not get a transmitter from Tetra");
	}

	private Receiver getDefaultDumpLocalReceiver() {
		return new DumpReceiver(System.out);
	}

	public Link buildFromKeyboardToTetra() {
		return build(tryToGetTetraOrDefaultToDumpReceiver(),
				tryToGetKeyboardOrDefaultDummyTransmitter());
	}

	Transmitter tryToGetKeyboardOrDefaultDummyTransmitter() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (!AkaiMap.isLpk25(info))
				continue;
			MidiDevice device;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxTransmitters() == 0)
					continue;
				device.open();
				System.out.println("connected to " + info.getName()
						+ " transmitter");
				return device.getTransmitter();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		return getDefaultDummyLocalTansmitter();
	}
}
