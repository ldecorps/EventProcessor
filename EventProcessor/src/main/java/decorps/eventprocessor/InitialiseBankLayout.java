package decorps.eventprocessor;

import static decorps.eventprocessor.vendors.livid.BankLayout.*;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;
import decorps.eventprocessor.vendors.maps.DefaultControllerParameterMap;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class InitialiseBankLayout {

	public final EventProcessor eventProcessor;
	static boolean isInitialised = false;

	public InitialiseBankLayout(EventProcessor eventProcessor) {
		this.eventProcessor = eventProcessor;
	}

	public void initialise() throws InterruptedException {
		if (isInitialised)
			return;
		isInitialised = true;
		setProgramMode();
		Thread.sleep(500);
		requestCurrentEditBufferAndWaitForAnswer();
		applyMapping();
		setEncosionMode();
		setLedRingStyles();
		setEncoderSpeed();
	}

	private void setEncoderSpeed() {
		System.out.println("set encoder speed");
		eventProcessor.sendToLivid(LividMessageFactory.build_Set_Encoder_Speed(
				0x44, 4));
	}

	private void setEncosionMode() {
		System.out.println("set encosion mode");
		eventProcessor.sendToLivid(LividMessageFactory
				.buildMap_Encosion_Mode(CurrentBank.getEncoderModes()));
	}

	private void setLedRingStyles() {
		System.out.println("set led ring style");
		eventProcessor.sendToLivid(LividMessageFactory
				.buildLED_Ring_Style(CurrentBank.getEncoderSyles()));
	}

	private void applyMapping() {
		System.out.println("applying mapping");
		MapRepository
				.register(new DefaultControllerParameterMap(
						programParameterData.A.oscillator1Frequency,
						Bank1.encoders[0]));
	}

	private void requestCurrentEditBufferAndWaitForAnswer()
			throws InterruptedException {
		System.out.println("request current edit buffer");
		eventProcessor.sendToTetra(DsiMessageFactory
				.buildProgramEditBufferDumpRequest());
		Thread.sleep(800);

	}

	private void setProgramMode() {
		System.out.println("program mode");
		eventProcessor.sendToTetra(DsiMessageFactory
				.buildMode_Change__ProgramChange());
	}
}
