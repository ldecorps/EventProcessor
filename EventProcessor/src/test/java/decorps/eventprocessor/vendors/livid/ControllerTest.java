package decorps.eventprocessor.vendors.livid;

import java.util.Random;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;

public class ControllerTest {

	public static final Controller nullController = new Controller() {

		public void setValue(byte value) {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public byte getRebasedValue() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public byte getValue() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public byte getId() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public ProgramParameter getProgramParameter() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public Mode getMode() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public int getCCOrNoteNumber() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public void setProgramParameter(ProgramParameter programParameter) {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public boolean isAbsolute() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public boolean isButton() {
			throw new EventProcessorException("Not Implemented Yet");
		}

		public Encoder asEncoder() {
			throw new EventProcessorException("Not Implemented Yet");
		}
	};
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
