package decorps.eventprocessor;

import static decorps.eventprocessor.vendors.livid.BankLayout.CurrentBank;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class InitialiseBankLayout {

	public final EventProcessor eventProcessor;
	static boolean isInitialised = false;

	public InitialiseBankLayout(EventProcessor eventProcessor) {
		this.eventProcessor = eventProcessor;
	}

	public synchronized void initialise() throws InterruptedException {
		if (!isInitialised) {
			isInitialised = true;
			setProgramMode();
			requestCurrentEditBufferAndWaitForAnswer();
			applyMapping();
			setEncosionMode();
			setLocalControl();
			setLedRingStyles();
			setEncoderSpeed();
			setButtonToggleModeEnable();
		}
	}

	private void setButtonToggleModeEnable() {
		System.out.println("enabling toggle mode");
		int[] allToggles = new int[32];
		for (int i = 0; i < 32; i++)
			allToggles[i] = 1;
		final EventProcessorMidiMessage build_Button_Toggle_Mode_Enable = LividMessageFactory
				.build_Button_Toggle_Mode_Enable(allToggles);
		eventProcessor.sendToLivid(build_Button_Toggle_Mode_Enable);

	}

	private void setLocalControl() {
		System.out.println("set encoder speed");
		eventProcessor.sendToLivid(LividMessageFactory
				.build_Switch_Relative_Local_Control_Off());
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
		MapRepository.initialise();
	}

	private void requestCurrentEditBufferAndWaitForAnswer()
			throws InterruptedException {
		System.out.println("request current edit buffer");
		eventProcessor.sendToTetra(DsiMessageFactory
				.buildProgramEditBufferDumpRequest());
		while (ProgramParameter.nullParameter.equals(BankLayout
				.getCurrentProgramParameterData())
				|| (null == BankLayout.getCurrentProgramParameterData())) {
			Thread.sleep(1000);
			eventProcessor.sendToTetra(DsiMessageFactory
					.buildProgramEditBufferDumpRequest());
		}

	}

	private void setProgramMode() {
		System.out.println("program mode");
		eventProcessor.sendToTetra(DsiMessageFactory
				.buildMode_Change__ProgramChange());
	}
}
