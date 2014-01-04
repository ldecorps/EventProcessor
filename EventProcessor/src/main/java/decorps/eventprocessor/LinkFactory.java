package decorps.eventprocessor;

import java.util.Set;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.akai.AkaiMap;
import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.korg.KorgMap;
import decorps.eventprocessor.utils.DumpReceiver;

public class LinkFactory {

	public final Set<Action> actions;

	LinkFactory(Set<Action> actions) {
		this.actions = actions;
	}

	public Link buildFromTetraToTetraIfPluggedIn() {
		return build(tryToGetTetraOrDefaultDummyTransmitter(),
				tryToGetTetraOrDefaultToDumpReceiver());
	}

	private Transmitter tryToGetTetraOrDefaultDummyTransmitter() {
		if (isTetraPluggedIn())
			return getTetraTransmitter();
		return getDefaultDummyLocalTansmitter();
	}

	Link build(Transmitter transmitter, Receiver receiver) {
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
		System.out.println("connected to dummy transmitter");
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

	public static boolean isKorgMicroKey25PluggedIn() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (KorgMap.isMicroKey25(info))
				return true;
		}
		return false;
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
				device.open();
				System.out
						.println("opening " + info.getName() + " transmitter");
				return device.getTransmitter();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		throw new EventProcessorException(
				"Could not get a transmitter from Tetra");
	}

	Receiver getDefaultDumpLocalReceiver() {
		System.out.println("Connected to DumpReceiver");
		return new DumpReceiver(System.out);
	}

	public Link buildFromKeyboardToTetra() {
		return build(tryToGetKeyboardOrDefaultDummyTransmitter(),
				tryToGetTetraOrDefaultToDumpReceiver());
	}

	Transmitter tryToGetKeyboardOrDefaultDummyTransmitter() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (!AkaiMap.isLpk25(info) && !KorgMap.isMicroKey25(info))
				continue;
			MidiDevice device;
			try {
				device = MidiSystem.getMidiDevice(info);
				if (device.getMaxTransmitters() == 0)
					continue;
				device.open();
				System.out
						.println("opening " + info.getName() + " transmitter");
				return device.getTransmitter();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		return getDefaultDummyLocalTansmitter();
	}
}
