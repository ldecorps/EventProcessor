package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public class MapRepository {

	static final List<EventProcessorMap> maps = new ArrayList<EventProcessorMap>();

	public static List<Controller> getControllersForParameter(
			Class<? extends AbstractProgramParameter> abstractProgramParameterClass) {
		for (EventProcessorMap map : maps) {
			if (map.getAbstractProgramParameterClass().equals(
					abstractProgramParameterClass))
				return map.getControllers();
		}

		throw new EventProcessorException("Could not find controller(s)");
	}

	public static void register(EventProcessorMap eventProcessorMap) {
		maps.add(eventProcessorMap);
	}

}
