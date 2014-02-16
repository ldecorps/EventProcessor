package decorps.eventprocessor.vendors.maps;

import static decorps.eventprocessor.vendors.maps.DefaultControllerParameterMap.mapToEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.exceptions.NoMapLeftToDefaultException;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;

public class MapRepository {

	public static final Set<EventProcessorMap> maps = new HashSet<EventProcessorMap>(
			128);

	static void completeInitialisationWithDefaultMaps() {
		final int size = MapRepository.maps.size();
		for (int i = size; i < 32; i++) {
			ProgramParameter programParameter = ProgramParameter.nullParameter;
			try {
				final DefaultControllerParameterMap map = createMapForNextAvailableParameterAndNextAvailableController();
				if (null == map)
					throw new EventProcessorException("Map not initialised");
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
		registerSpecificMaps();
		completeInitialisationWithDefaultMaps();
	}

	private static void registerSpecificMaps() {
		new Oscillator1ShapeMap();
		mapToEncoder(Osc1FineFreq.class, 8);

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
		BankLayout.getCurrentBank().encoders[controller.getId()]
				.setProgramParameter(programParameter);
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
		DefaultControllerParameterMap map = new DefaultControllerParameterMap(
				nextParameterNotMapped, nextControllerNotMapped);
		return map;
	}

	static Controller nextControllerNotMapped() {
		for (Controller controller : BankLayout.CurrentBank.encoders)
			if (controller.getProgramParameter().equals(
					ProgramParameter.nullParameter))
				return controller;
		throw new EventProcessorException("All Controllers are mapped: " + maps);
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
