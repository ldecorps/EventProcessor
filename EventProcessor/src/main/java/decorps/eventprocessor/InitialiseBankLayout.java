package decorps.eventprocessor;

import static decorps.eventprocessor.vendors.livid.BankLayout.*;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.Encoder;
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
		if (!isInitialised) {
			isInitialised = true;
			setProgramMode();
			Thread.sleep(500);
			requestCurrentEditBufferAndWaitForAnswer();
			return;
		}
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
		final int[] encoderModes = CurrentBank.getEncoderModes();
		final EventProcessorMidiMessage buildMap_Encosion_Mode = LividMessageFactory
				.buildMap_Encosion_Mode(encoderModes);
		eventProcessor.sendToLivid(buildMap_Encosion_Mode);
	}

	private void setLedRingStyles() {
		System.out.println("set led ring style");
		eventProcessor.sendToLivid(LividMessageFactory
				.buildLED_Ring_Style(CurrentBank.getEncoderSyles()));
	}

	private void applyMapping() {
		System.out.println("applying mapping");
		final Encoder encoder = Bank1.encoders[0];
		final ProgramParameter oscillator1Frequency = programParameterData.A.oscillator1Frequency;
		final DefaultControllerParameterMap eventProcessorMap = new DefaultControllerParameterMap(
				oscillator1Frequency, encoder);
		MapRepository.register(eventProcessorMap);
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
