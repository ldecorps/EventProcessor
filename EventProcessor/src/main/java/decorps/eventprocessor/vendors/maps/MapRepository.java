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
			32);

	static {
		initialiseCurrentBank();
	}

	public static void initialiseCurrentBank() {
		maps.clear();
		for (int i = 0; i < 32; i++) {
			ProgramParameter programParameter = ProgramParameter.nullParameter;
			try {
				final DefaultControllerParameterMap map = createMapForNextAvailableParameterAndNextAvailableController();
				programParameter = map.getProgramParameter();
				register(map);
			} catch (NoMapLeftToDefaultException e) {
				return;
			}
			BankLayout.CurrentBank.encoders[i]
					.setProgramParameter(programParameter);
		}
	}

	MapRepository() {
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
		for (EventProcessorMap map : maps) {
			if (map.getControllers().contains(
					eventProcessorMap.getControllers()))
				maps.remove(map);
		}
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
		DefaultControllerParameterMap map = new DefaultControllerParameterMap(
				nextParameterNotMapped(), nextControllerNotMapped());
		return map;
	}

	static Controller nextControllerNotMapped() {
		byte greaterMappedController = -1;
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
				if (controllerCandidate.getId() == controller.getId())
					return controllerCandidate.getProgramParameter();
		}
		throw new EventProcessorException(
				"could not find parameter for controller " + controller);
	}

	public static void map(ProgramParameterData unpacked) {
		for (ProgramParameter programParameter : unpacked
				.getFirst200AbstractProgramParameters()) {
			EventProcessorMap mapsForThisParameter = getMapForProgramParameter(programParameter);
			mapsForThisParameter.map(programParameter);
		}
		System.out.println(unpacked.Name + " mapped");
	}

	private static EventProcessorMap getMapForProgramParameter(
			ProgramParameter programParameter) {
		for (EventProcessorMap map : maps) {
			if (map.getProgramParameter().getClass()
					.equals(programParameter.getClass())) {
				return map;
			}
		}
		throw new EventProcessorException("could not find map for parameter: "
				+ programParameter + ". Maps: " + maps);
	}
}
