package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.rules.Rule;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;
import decorps.eventprocessor.vendors.dsi.TetraParameter;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class EventProcessor {
	public final Link fromTetraToLivid;
	public final Link fromLividToTetra;
	public final Link fromTetraToTetra;
	final Set<Action> actions;
	final LinkFactory linkFactory;
	public final List<Link> links = new ArrayList<Link>();
	private static EventProcessor instance;

	static EventProcessor build() {
		if (null == instance) {
			instance = new EventProcessor();
			instance.initialise();
			MapRepository.initialiseCurrentBank();
		}
		return instance;
	}

	public static EventProcessor getInstance() {
		return build();
	}

	EventProcessor() {
		actions = new HashSet<Action>();
		linkFactory = new LinkFactory(actions);
		fromTetraToLivid = linkFactory.buildFromTetraToLivid();
		fromLividToTetra = linkFactory.buildFromLividToTetra();
		fromTetraToTetra = linkFactory.buildFromTetraToTetra();
		links.add(fromTetraToLivid);
		links.add(fromLividToTetra);
	}

	public void close() {
		for (Link link : links) {
			link.transmitter.close();
			link.receiver.close();
		}
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
		return fromTetraToLivid.transmitter.getReceiver();
	}

	public static boolean isTetraPluggedIn() {
		for (MidiDevice.Info info : LinkFactory.midiDeviceInfos) {
			if (DsiTetraMap.isTetra(info))
				return true;
		}
		return false;
	}

	private Receiver getTetraReceiver() {
		for (MidiDevice.Info info : LinkFactory.midiDeviceInfos) {
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
		registerAction(rule, TetraParameter.ANY_MESSAGE, fromTetraToLivid);
	}

	public void registerAction(Rule rule, TetraParameter tetraParameter,
			Link link) {
		registerAction(rule, tetraParameter, link.transmitter, link.receiver);
	}

	public void registerAction(Rule rule, TetraParameter tetraParameter,
			Transmitter from, Receiver to) {
		actions.add(Action.build(rule, tetraParameter, from, to));
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void sendToTetra(EventProcessorMidiMessage message) {
		fromTetraToTetra.receiver.getRawReceiver().send(message, -1);
	}

	public void sendToLivid(EventProcessorMidiMessage message) {
		fromTetraToLivid.receiver.getRawReceiver().send(message, -1);
	}

	public void initialise() {
		new InitialiseBankLayout(this);
	}

}
