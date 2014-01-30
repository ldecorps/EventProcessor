import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.run();
	}

	public void run() throws InterruptedException {
		EventProcessor eventProcessor = EventProcessor.build();
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, eventProcessor.fromTetraToTetra);
		eventProcessor.registerAction(new SetEncodersAndLedIndicatorsRule(),
				TetraParameter.ProgramEditBufferDataDump,
				eventProcessor.fromTetraToLivid);
		synchronized (this) {
			this.wait();
		}
	}

}
