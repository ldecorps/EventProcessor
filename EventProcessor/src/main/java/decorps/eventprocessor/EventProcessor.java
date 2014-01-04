package decorps.eventprocessor;

import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.dsi.DsiTetraMap;
import decorps.eventprocessor.dsi.TetraParameter;
import decorps.eventprocessor.rules.Rule;

public class EventProcessor {
	public final Link fromKeyboardToTetra;
	final public Link fromTetraToTetra;
	final Set<Action> actions;
	final LinkFactory linkFactory;

	protected EventProcessor() {
		actions = new HashSet<Action>();
		linkFactory = new LinkFactory(actions);
		fromKeyboardToTetra = linkFactory.buildFromKeyboardToTetra();
		fromTetraToTetra = linkFactory.buildFromTetraToTetraIfPluggedIn();
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
		return fromTetraToTetra.transmitter.getReceiver();
	}

	public static EventProcessor build() {
		return new EventProcessor();
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
		registerAction(rule, TetraParameter.ANY_MESSAGE, fromTetraToTetra);
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
		registerAction(rule, tetraParameter, fromTetraToTetra);
	}

	public Set<Action> getActions() {
		return actions;
	}

}
