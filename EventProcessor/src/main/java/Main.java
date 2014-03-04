import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.InitialiseBankLayout;
import decorps.eventprocessor.rules.LividEncoderOrButtonValue_NewValue_SendToTetra;
import decorps.eventprocessor.rules.ProgramEditBufferDumpRequest;
import decorps.eventprocessor.rules.RelativeEncoderChangeEchoesNewLEDRingValue;
import decorps.eventprocessor.rules.SetEncodersAndLedIndicatorsRule;
import decorps.eventprocessor.vendors.dsi.MessageType;

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
				MessageType.ProgramChange, eventProcessor.fromTetraToTetra);
		eventProcessor.registerAction(
				new LividEncoderOrButtonValue_NewValue_SendToTetra(),
				MessageType.ANY_NOTE_OR_CC, eventProcessor.fromLividToTetra);
		eventProcessor.registerAction(new SetEncodersAndLedIndicatorsRule(),
				MessageType.ProgramEditBufferDataDump,
				eventProcessor.fromTetraToLivid);
		eventProcessor.registerAction(
				new RelativeEncoderChangeEchoesNewLEDRingValue(),
				MessageType.RELATIVE_ONLY, eventProcessor.fromLividToLivid);
	}

}
