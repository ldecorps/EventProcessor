package decorps.eventprocessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.rules.Rule;

public class EventProcessor implements MidiDevice {
	public Link fromTetra;
	Set<Action> actions = new HashSet<Action>();

	protected EventProcessor() {
		this.fromTetra = Link.build(RulesAwareReceiverWrapper.build(
				tryToGetTetraOrDefaultToDummyReceiver(), actions),
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
		return fromTetra.transmitter.getReceiver();
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
		fromTetra.open();
	}

	@Override
	public void close() {
		fromTetra.close();
	}

	@Override
	public boolean isOpen() {
		return fromTetra.isOpen();
	}

	@Override
	public long getMicrosecondPosition() {
		return fromTetra.getMicrosecondPosition();
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
		return fromTetra.receiver;
	}

	@Override
	public List<Receiver> getReceivers() {
		return Arrays.asList(new Receiver[] { fromTetra.receiver });
	}

	@Override
	public Transmitter getTransmitter() {
		return fromTetra.transmitter;
	}

	@Override
	public List<Transmitter> getTransmitters() {
		return Arrays.asList(new Transmitter[] { fromTetra.transmitter });
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

	public void registerDefaultRule(Rule rule) {
		registerAction(rule, TetraParameter.ANY_MESSAGE, fromTetra);
	}

	public void registerAction(Rule rule, TetraParameter tetraParameter,
			Transmitter from, Receiver to) {
		actions.add(Action.build(rule, tetraParameter, from, to));
	}

	public void registerAction(Rule rule, TetraParameter tetraParameter,
			Link link) {
		registerAction(rule, tetraParameter, link.transmitter, link.receiver);
	}

	public void registerAction(Rule rule, TetraParameter tetraParameter) {
		registerAction(rule, tetraParameter, fromTetra);
	}

	public Set<Action> getActions() {
		return actions;
	}
}
