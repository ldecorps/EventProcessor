package decorps.eventprocessor.vendors.maps;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class OscillatorButton_to_OscillatorShapeMap extends DefaultMap {

	public OscillatorButton_to_OscillatorShapeMap(
			Class<? extends ProgramParameter> abstractProgramParameterClass,
			int controllerNumber) {
		super(abstractProgramParameterClass, controllerNumber);
	}

	@Override
	public EventProcessorMidiMessage mapFromLividToTetra(
			EventProcessorMidiMessage messageIn) {
		EventProcessorShortMessage shortMessage = messageIn.getAsShortMessage();
		if (shortMessage.isNoteOff())
			return setToMute();
		return reactivatedWaveForm();
	}

	public EventProcessorMidiMessage reactivatedWaveForm() {
		int currentlySelectedWaveForm = 1;
		for (int i = 1; i <= 3; i++) {
			if (BankLayout.CurrentBank.buttons[i].isSwitchedOn())
				currentlySelectedWaveForm = i;
		}
		return DsiMessageFactory
				.buildNRPNForProgramParameter(new Oscillator1Shape(1,
						(byte) currentlySelectedWaveForm));

	}

	public EventProcessorMidiMessage setToMute() {
		return DsiMessageFactory
				.buildNRPNForProgramParameter(new Oscillator1Shape(2, (byte) 0));
	}

	@Override
	public EventProcessorMidiMessage mapFromTetraToLivid(
			EventProcessorMidiMessage messageIn) {
		throw new EventProcessorException("Not Implemented Yet");
	}

}
