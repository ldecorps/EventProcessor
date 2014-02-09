package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.exceptions.NoMapLeftToDefaultException;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;

public class MapRepository {

	public static final Set<EventProcessorMap> maps = new HashSet<EventProcessorMap>(
			128);

	public static void initialiseCurrentBank() {
		maps.clear();
		for (int i = 0; i < 32; i++) {
			ProgramParameter programParameter = ProgramParameter.nullParameter;
			try {
				final DefaultControllerParameterMap map = createMapForNextAvailableParameterAndNextAvailableController();
				if (null == map)
					throw new EventProcessorException("Map not initialised");
				programParameter = map.getProgramParameter();
			} catch (NoMapLeftToDefaultException e) {
				return;
			}
			BankLayout.CurrentBank.encoders[i]
					.setProgramParameter(programParameter);
		}
	}

	MapRepository() {
		initialiseCurrentBank();
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
		Controller controller = eventProcessorMap.getControllers().get(0);

		BankLayout.CurrentBank.encoders[controller.getId()]
				.setProgramParameter(programParameter);
	}

	public static boolean contains(EventProcessorMap map) {
		return (maps.contains(map));
	}

	static ProgramParameter nextParameterNotMapped()
			throws NoMapLeftToDefaultException {
		ProgramParameter programParameter = ProgramParameter.nullParameter;
		for (EventProcessorMap map : maps) {
			final ProgramParameter candidateProgramParameter = map
					.getProgramParameter();
			if (candidateProgramParameter.getLayerANRPNNumber() > programParameter
					.getLayerANRPNNumber()) {
				programParameter = candidateProgramParameter;
			}
		}
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
		int greaterMappedController = -1;
		for (EventProcessorMap map : maps)
			for (Controller controllerCandidate : map.getControllers())
				if (controllerCandidate.getId() > greaterMappedController)
					greaterMappedController = controllerCandidate.getId();
		for (Controller controller : BankLayout.CurrentBank.encoders)
			if (controller.getId() > greaterMappedController)
				return controller;
		throw new EventProcessorException("All Controllers are mapped: " + maps);
	}

	public static ProgramParameter getParameterForController(
			Controller controller) {
		for (EventProcessorMap map : maps) {
			for (Controller controllerCandidate : map.getControllers())
				if (controllerCandidate.equals(controller))
					return controllerCandidate.getProgramParameter();
		}
		throw new EventProcessorException(
				"could not find parameter for controller " + controller
						+ ". Maps: " + maps);
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
