package decorps.eventprocessor.snipets;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.vendors.dsi.MessageType;

public class TestingProgramChange {

	public static void main(String[] args) throws InterruptedException {
		EventProcessor eventProcessor = EventProcessor.getInstance();
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				MessageType.ProgramChange, eventProcessor.fromTetraToTetra);
		eventProcessor.registerAction(new SetEncodersAndLedIndicatorsRule(),
				MessageType.ProgramEditBufferDataDump,
				eventProcessor.fromTetraToLivid);
		synchronized (TestingProgramChange.class) {
			TestingProgramChange.class.wait();
		}
	}
}
