package decorps.eventprocessor;

import java.util.Arrays;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.rules.Rule;

public class EventProcessor implements MidiDevice {
	public Link link;

	protected EventProcessor() {
		this.link = Link.build(RulesAwareReceiverWrapper
				.build(tryToGetTetraOrDefaultToDummyReceiver()),
				getDefaultDummyLocalTansmitter());
	}

	Receiver tryToGetTetraOrDefaultToDummyReceiver() {
		if (isTetraPluggedIn())
			return getTetraReceiver();
		return new DummyReceiver();
	}

	Transmitter getDefaultDummyLocalTansmitter() {
		return new DummyTransmitter();
	}

	Receiver getDefaultRemoteReceiver() {
		return link.transmitter.getReceiver();
	}

	public static EventProcessor build() {
		return new EventProcessor();
	}

	@Override
	public Info getDeviceInfo() {
		return new EventProcessorInfo("EventProcessor", "LDECORPS",
				"EventProcessor", "1");
	}

	@Override
	public void open() {
		link.open();
	}

	@Override
	public void close() {
		link.close();
	}

	@Override
	public boolean isOpen() {
		return link.isOpen();
	}

	@Override
	public long getMicrosecondPosition() {
		return link.getMicrosecondPosition();
	}

	@Override
	public int getMaxReceivers() {
		return 1;
	}

	@Override
	public int getMaxTransmitters() {
		return 1;
	}

	@Override
	public Receiver getReceiver() {
		return link.receiver;
	}

	@Override
	public List<Receiver> getReceivers() {
		return Arrays.asList(new Receiver[] { link.receiver });
	}

	@Override
	public Transmitter getTransmitter() {
		return link.transmitter;
	}

	@Override
	public List<Transmitter> getTransmitters() {
		return Arrays.asList(new Transmitter[] { link.transmitter });
	}

	public void addRule(Rule rule) {
		link.receiver.add(rule);
	}

	public static boolean isTetraPluggedIn() {
		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (DsiTetraMap.isTetra(info))
				return true;
		}
		return false;
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
				return device.getReceiver();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		throw new EventProcessorException("Could not get a receiver from Tetra");
	}
}
