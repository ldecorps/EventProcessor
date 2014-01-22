package decorps.eventprocessor.snipets;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.SetRingIndicatorsViaCCsRule;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class TestingProgramChange {

	public static void main(String[] args) throws InterruptedException {
		EventProcessor eventProcessor = new EventProcessor();
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, eventProcessor.fromTetraToTetra);
		eventProcessor.registerAction(new SetRingIndicatorsViaCCsRule(),
				TetraParameter.ProgramEditBufferDataDump,
				eventProcessor.fromTetraToLivid);
		synchronized (TestingProgramChange.class) {
			TestingProgramChange.class.wait();
		}
	}
}
