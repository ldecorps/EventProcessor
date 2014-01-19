package decorps.eventprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.utils.DumpReceiver;
import decorps.eventprocessor.vendors.akai.AkaiMap;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;
import decorps.eventprocessor.vendors.korg.KorgMap;
import decorps.eventprocessor.vendors.livid.LividCodev2Map;

public class LinkFactory {

	public final Set<Action> actions;
	public static final Info[] midiDeviceInfos;

	static {
		midiDeviceInfos = LinkFactory.getMidiDeviceInfo();
	}

	LinkFactory(Set<Action> actions) {
		this.actions = actions;
	}

	public Link buildFromTetraToTetraIfPluggedIn() {
		return build(tryToGetTetraOrTestingTetraTransmitter(),
				tryToGetTetraOrDefaultToDumpReceiver());
	}

	private Transmitter tryToGetTetraOrTestingTetraTransmitter() {
		if (isTetraPluggedIn())
			return getTetraTransmitter();
		return buildTestingTetraTransmitter();
	}

	public Transmitter buildTestingTetraTransmitter() {
		try {
			return (Transmitter) Class
					.forName(
							"decorps.eventprocessor.vendors.dsi.TestingTetraTransmitter")
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
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
		for (MidiDevice.Info info : midiDeviceInfos) {
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

	private Receiver getLividCode2Receiver() {
		for (MidiDevice.Info info : midiDeviceInfos) {
			if (!LividCodev2Map.isCodeV2(info))
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
		throw new EventProcessorException(
				"Could not get a receiver from Livid Code2");
	}

	public static boolean isTetraPluggedIn() {
		for (MidiDevice.Info info : midiDeviceInfos) {
			if (DsiTetraMap.isTetra(info))
				return true;
		}
		return false;
	}

	public static boolean isAkaiLpk25PluggedIn() {
		for (MidiDevice.Info info : midiDeviceInfos) {
			if (AkaiMap.isLpk25(info))
				return true;
		}
		return false;
	}

	public static boolean isKorgMicroKey25PluggedIn() {
		for (MidiDevice.Info info : midiDeviceInfos) {
			if (KorgMap.isMicroKey25(info))
				return true;
		}
		return false;
	}

	private Transmitter getTetraTransmitter() {
		for (MidiDevice.Info info : midiDeviceInfos) {
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
		for (MidiDevice.Info info : midiDeviceInfos) {
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

	private static Info[] getMidiDeviceInfo() {
		Info[] infos = MidiSystem.getMidiDeviceInfo();
		if (!isMmjRunning(infos))
			return infos;
		List<Info> infoList = new ArrayList<Info>();
		for (Info info : infos) {
			if (isMmjAware(infos, info.getName()))
				infoList.add(info);
		}
		return infoList.toArray(new Info[infoList.size()]);
	}

	public Link buildFromLocalToTetraIfPluggedIn() {
		return build(getDefaultDummyLocalTansmitter(),
				tryToGetTetraOrDefaultToDumpReceiver());
	}

	public Link buildFromTetraIfPluggedInToLocal() {
		return build(tryToGetTetraOrTestingTetraTransmitter(),
				getDefaultDumpLocalReceiver());
	}

	public Link buildFromTetraToLivid() {
		return build(tryToGetTetraOrTestingTetraTransmitter(),
				tryToGetLividCode2OrTestingLivideCode2Receiver());
	}

	public Link buildFromLividToTetra() {
		return build(tryToGetLividOrTestingTransmitter(),
				tryToGetTetraOrTestingTetraReceiver());
	}

	private Receiver tryToGetTetraOrTestingTetraReceiver() {
		if (isTetraPluggedIn())
			return getTetraReceiver();
		else
			return buildTestingTetraReceiver();
	}

	public Receiver buildTestingTetraReceiver() {
		try {
			return (Receiver) Class.forName(
					"decorps.eventprocessor.vendors.dsi.TestingTetraReceiver")
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	private Transmitter tryToGetLividOrTestingTransmitter() {
		if (isLividCodev2PluggedIn())
			return getLividTransmitter();
		return buildTestingLividTransmitter();
	}

	public Transmitter buildTestingLividTransmitter() {
		try {
			return (Transmitter) Class
					.forName(
							"decorps.eventprocessor.vendors.livid.TestingLividTransmitter")
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	private Transmitter getLividTransmitter() {
		for (MidiDevice.Info info : midiDeviceInfos) {
			if (!LividCodev2Map.isCodeV2(info))
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
				"Could not get a transmitter from Livid");
	}

	private Receiver tryToGetLividCode2OrTestingLivideCode2Receiver() {
		if (isLividCodev2PluggedIn())
			return getLividCode2Receiver();
		System.out.println("Connecting to Testing Livid Receiver");
		return buildTesgingLividCode2Receiver();
	}

	public Receiver buildTesgingLividCode2Receiver() {
		try {
			return (Receiver) Class
					.forName(
							"decorps.eventprocessor.vendors.livid.TestingLividCode2Receiver")
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	public static boolean isLividCodev2PluggedIn() {
		for (MidiDevice.Info info : midiDeviceInfos) {
			if (LividCodev2Map.isCodeV2(info))
				return true;
		}
		return false;
	}

	public Link buildFromLocalToLocal() {
		return build(getDefaultDummyLocalTansmitter(),
				getDefaultDumpLocalReceiver());
	}

	static boolean isMmjRunning(final Info[] infos) {
		String[] names = new String[infos.length];
		int i = 0;
		for (Info info : infos) {
			names[i++] = getMmjStyleName(info);
		}

		for (String name : names) {
			if (isMmjAware(infos, name))
				return true;
		}
		return false;
	}

	public static String getMmjStyleName(Info info) {
		return info.getDescription().replace(" " + info.getName(), "") + " - "
				+ info.getName();
	}

	public static boolean isMmjAware(final Info[] infos, String candidate) {
		for (Info info : infos) {
			if (candidate.equals(getMmjStyleName(info)))
				return true;
		}
		return false;
	}

	public Link buildFromTetraToTetra() {
		return build(tryToGetTetraOrTestingTetraTransmitter(),
				tryToGetTetraOrTestingTetraReceiver());
	}
}
