package decorps.eventprocessor.vendors.livid;

import java.util.Random;

import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;

public class ControllerTest {

	static int testEncoderNextId = 0;

	public static Controller newAbsoluteEncoderController() {

		final Encoder encoder = BankLayout.CurrentBank.encoders[testEncoderNextId++];
		byte[] bytes = new byte[2];
		new Random().nextBytes(bytes);
		encoder.setProgramParameter(ProgramParameterTest
				.newSampleAbsoluteParameter());
		encoder.setValue(bytes[0]);
		return encoder;
	}

}
