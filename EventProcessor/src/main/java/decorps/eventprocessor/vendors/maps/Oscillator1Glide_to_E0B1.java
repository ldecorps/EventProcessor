package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.livid.Controller;

public class Oscillator1Glide_to_E0B1 extends DefaultControllerParameterMap implements
		EventProcessorMap {

	public Oscillator1Glide_to_E0B1(
			AbstractProgramParameter abstractProgramParameter,
			Controller... controllers) {
		super(abstractProgramParameter, controllers);
	}

}
