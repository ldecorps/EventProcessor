package decorps.eventprocessor.vendors.maps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;

public class MapRepository {

	private static final Set<EventProcessorMap> maps = new HashSet<EventProcessorMap>(
			32);

	static {
		initialiseCurrentBank();
	}

	public static void initialiseCurrentBank() {
		for (int i = 0; i < 32; i++)
			getMaps().add(
					new DefaultControllerParameterMap(
							AbstractProgramParameter.nullParameter,
							BankLayout.CurrentBank.encoders[i]));
	}

	private MapRepository() {
	}

	public static List<Controller> getControllersForParameter(
			AbstractProgramParameter abstractProgramParameter) {
		List<Controller> result = new ArrayList<Controller>();
		for (EventProcessorMap map : getMaps()) {
			if (map.getAbstractProgramParameter().getLayerANRPNNumber() == abstractProgramParameter
					.getLayerANRPNNumber())
				result.addAll(map.getControllers());
		}
		return result;
	}

	public static void register(EventProcessorMap eventProcessorMap) {
		getMaps().add(eventProcessorMap);
	}

	public static Set<EventProcessorMap> getMaps() {
		return maps;
	}

	public static boolean contains(EventProcessorMap map) {
		return (maps.contains(map));
	}
}
