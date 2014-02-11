import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.InitialiseBankLayout;
import decorps.eventprocessor.rules.LividEncoderOrButtonValueNewValue_SentToTetra;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.vendors.dsi.TetraParameter;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.run();
	}

	public void run() throws InterruptedException {
		EventProcessor eventProcessor = EventProcessor.getInstance();

		registerActions(eventProcessor);

		new InitialiseBankLayout(eventProcessor).initialise();
		synchronized (this) {
			this.wait();
		}
	}

	private void registerActions(EventProcessor eventProcessor) {
		eventProcessor.registerAction(new ProgramEditBufferDumpRequest(),
				TetraParameter.ProgramChange, eventProcessor.fromTetraToTetra);
		eventProcessor.registerAction(
				new LividEncoderOrButtonValueNewValue_SentToTetra(),
				TetraParameter.ANY_NOTE_OR_CC, eventProcessor.fromLividToTetra);
		eventProcessor.registerAction(new SetEncodersAndLedIndicatorsRule(),
				TetraParameter.ProgramEditBufferDataDump,
				eventProcessor.fromTetraToLivid);
	}

}
