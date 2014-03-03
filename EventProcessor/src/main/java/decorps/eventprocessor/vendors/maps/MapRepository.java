package decorps.eventprocessor.vendors.maps;

import static decorps.eventprocessor.vendors.maps.DefaultControllerParameterMap.mapToEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import decorps.eventprocessor.exceptions.NoMapLeftToDefaultException;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.ArpeggiatorMode;
import decorps.eventprocessor.vendors.dsi.programparameters.ClockBPM;
import decorps.eventprocessor.vendors.dsi.programparameters.ClockDivide;
import decorps.eventprocessor.vendors.dsi.programparameters.FeedbackGain;
import decorps.eventprocessor.vendors.dsi.programparameters.FeedbackVolume;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterAudioMod;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterCutoffFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvVelocity;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterKeyboardAmt;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterResonance;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO2Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod1Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.NoiseLevel;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator2Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.OscillatorMix;
import decorps.eventprocessor.vendors.dsi.programparameters.PanSpread;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramVolume;
import decorps.eventprocessor.vendors.dsi.programparameters.SubOsc1Level;
import decorps.eventprocessor.vendors.dsi.programparameters.SubOsc2Level;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvVelocity;
import decorps.eventprocessor.vendors.dsi.programparameters.VCALevel;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;

public class MapRepository {

	public static final Set<EventProcessorMap> maps = new HashSet<EventProcessorMap>(
			128);

	static void completeInitialisationWithDefaultMaps() {
		System.out.println("Completing with default maps...");
		final int size = MapRepository.maps.size();
		for (int i = size; i < 32; i++) {
			ProgramParameter programParameter = ProgramParameter.nullParameter;
			try {
				final DefaultControllerParameterMap map = createMapForNextAvailableParameterAndNextAvailableController();
				if (null == map)
					continue;
				programParameter = map.getProgramParameter();
				map.getControllers().get(0)
						.setProgramParameter(programParameter);
			} catch (NoMapLeftToDefaultException e) {
				return;
			}
		}
	}

	MapRepository() {
		initialise();
	}

	public static void initialise() {
		maps.clear();
		registerSpecificMaps();
		completeInitialisationWithDefaultMaps();
	}

	private static void registerSpecificMaps() {
		mapToEncoder(Osc1Frequency.class, 0);
		mapToEncoder(Osc1FineFreq.class, 8);
		mapToEncoder(SubOsc1Level.class, 16);
		// new Oscillator1ShapeMap();
		mapToEncoder(Oscillator1Shape.class, 24);

		mapToEncoder(Osc2Frequency.class, 1);
		mapToEncoder(Osc2FineFreq.class, 9);
		mapToEncoder(SubOsc2Level.class, 17);
		mapToEncoder(Oscillator2Shape.class, 25);
		// new Oscillator2ShapeMap();

		mapToEncoder(FilterCutoffFreq.class, 2);
		mapToEncoder(FilterResonance.class, 10);
		mapToEncoder(OscillatorMix.class, 18);
		mapToEncoder(NoiseLevel.class, 26);

		mapToEncoder(FeedbackVolume.class, 3);
		mapToEncoder(FeedbackGain.class, 11);
		mapToEncoder(FilterKeyboardAmt.class, 19);
		mapToEncoder(FilterAudioMod.class, 27);

		mapToEncoder(ProgramVolume.class, 4);
		mapToEncoder(FilterEnvAmount.class, 12);
		mapToEncoder(FilterEnvVelocity.class, 20);

		mapToEncoder(VCALevel.class, 5);
		mapToEncoder(VCAEnvAmount.class, 13);
		mapToEncoder(VCAEnvVelocity.class, 21);
		mapToEncoder(PanSpread.class, 29);

		mapToEncoder(LFO1Frequency.class, 6);
		mapToEncoder(LFO1Amount.class, 14);
		mapToEncoder(LFO1Shape.class, 22);
		mapToEncoder(LFO2Frequency.class, 30);

		mapToEncoder(ClockBPM.class, 7);
		mapToEncoder(ClockDivide.class, 15);
		mapToEncoder(Mod1Amount.class, 23);
		mapToEncoder(ArpeggiatorMode.class, 31);
	}

	public static List<Controller> getControllersForParameter(
			ProgramParameter programParameter) {
		List<Controller> result = new ArrayList<Controller>();
		for (EventProcessorMap map : maps) {
			if (map.getProgramParameter().getClass()
					.equals(programParameter.getClass()))
				result.addAll(map.getControllers());
		}
		return result;
	}

	public static void register(EventProcessorMap eventProcessorMap) {
		System.out.println("registering map " + eventProcessorMap);
		maps.add(eventProcessorMap);
		ProgramParameter programParameter = eventProcessorMap
				.getProgramParameter();
		for (Controller controller : eventProcessorMap.getControllers())
			assignProgramParameterToController(programParameter, controller);
	}

	private static void assignProgramParameterToController(
			ProgramParameter programParameter, Controller controller) {
		controller.setProgramParameter(programParameter);
	}

	public static boolean contains(EventProcessorMap map) {
		return (maps.contains(map));
	}

	static ProgramParameter nextParameterNotMapped()
			throws NoMapLeftToDefaultException {
		ProgramParameter programParameter = ProgramParameter.nullParameter;
		programParameter = getNextProgramParameter(programParameter);
		ProgramParameterData currentProgramParameterData = BankLayout
				.getCurrentProgramParameterData();
		if (null == currentProgramParameterData)
			return ProgramParameter.nullParameter;
		for (ProgramParameter nextProgramParameter : currentProgramParameterData
				.getAll400AbstractProgramParameters()) {
			if (nextProgramParameter.getLayerANRPNNumber() > programParameter
					.getLayerANRPNNumber())
				return nextProgramParameter;
		}

		throw new NoMapLeftToDefaultException(
				"All Program Parameters are mapped");
	}

	private static ProgramParameter getNextProgramParameter(
			ProgramParameter programParameter) {
		for (EventProcessorMap map : maps) {
			final ProgramParameter candidateProgramParameter = map
					.getProgramParameter();
			if (candidateProgramParameter.getLayerANRPNNumber() > programParameter
					.getLayerANRPNNumber()) {
				programParameter = candidateProgramParameter;
			}
		}
		return programParameter;
	}

	static DefaultControllerParameterMap createMapForNextAvailableParameterAndNextAvailableController()
			throws NoMapLeftToDefaultException {
		final ProgramParameter nextParameterNotMapped = nextParameterNotMapped();
		if (ProgramParameter.nullParameter.equals(nextParameterNotMapped))
			return null;
		final Controller nextControllerNotMapped = nextControllerNotMapped();
		if (null == nextControllerNotMapped)
			return null;
		DefaultControllerParameterMap map = new DefaultControllerParameterMap(
				nextParameterNotMapped, nextControllerNotMapped);
		return map;
	}

	static Controller nextControllerNotMapped() {
		for (Controller controller : BankLayout.CurrentBank.encoders)
			if (controller.getProgramParameter().equals(
					ProgramParameter.nullParameter))
				return controller;
		return null;
	}

	public static void map(ProgramParameterData programParameterData) {
		for (ProgramParameter programParameter : programParameterData
				.getFirst200AbstractProgramParameters()) {
			EventProcessorMap mapsForThisParameter;
			try {
				mapsForThisParameter = getMapForProgramParameter(programParameter);
			} catch (NoMapLeftToDefaultException e) {
				System.out.println("32 maps done");
				return;
			}
			mapsForThisParameter.map(programParameter);
		}
		System.out.println(programParameterData.Name + " mapped");
	}

	public static List<EventProcessorMap> getMapsForController(
			Controller controller) {
		List<EventProcessorMap> result = new ArrayList<EventProcessorMap>();
		for (EventProcessorMap map : maps) {
			if (map.getControllers().contains(controller))
				result.add(map);
		}
		return result;
	}

	private static EventProcessorMap getMapForProgramParameter(
			ProgramParameter programParameter)
			throws NoMapLeftToDefaultException {
		for (EventProcessorMap map : maps) {
			if (map.getProgramParameter().getClass()
					.equals(programParameter.getClass())) {
				return map;
			}
		}
		throw new NoMapLeftToDefaultException(
				"could not find map for parameter: " + programParameter
						+ " NRPN: " + programParameter.getLayerANRPNNumber()
						+ " in " + maps.size() + " maps: " + maps);
	}

}
