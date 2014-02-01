package decorps.eventprocessor.vendors.livid;

import java.util.Random;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameterTest;

public class ControllerTest {

	public static Controller newAbsoluteEncoderController() {
		final Encoder encoder = new Encoder();
		byte[] bytes = new byte[2];
		new Random().nextBytes(bytes);
		encoder.setProgramParameter(AbstractProgramParameterTest
				.newSampleAbsoluteParameter());
		encoder.setValue(bytes[0]);
		return encoder;
	}

}
