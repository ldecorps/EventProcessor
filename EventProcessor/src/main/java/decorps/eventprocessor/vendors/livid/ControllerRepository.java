package decorps.eventprocessor.vendors.livid;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.maps.EventProcessorMap;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class ControllerRepository {

	public static Controller getControllerForLividShortMessage(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		final boolean isEncoder = eventProcessorMidiMessage.getAsShortMessage()
				.isCC();
		final int controllerIndex = getControllerIndexForCcOrNoteInShortMessage(eventProcessorMidiMessage);
		final Controller controller = isEncoder ? BankLayout.CurrentBank.encoders[controllerIndex]
				: BankLayout.CurrentBank.buttons[controllerIndex];
		return controller;
	}

	private static int getControllerIndexForCcOrNoteInShortMessage(
			EventProcessorMidiMessage eventProcessorMidiMessage) {
		final EventProcessorShortMessage asShortMessage = eventProcessorMidiMessage
				.getAsShortMessage();
		final int CcNumberOrNote = asShortMessage.getData1();
		int id = 0;
		if (asShortMessage.isCC())
			id = Encoder.getIdForCc(CcNumberOrNote);
		else
			id = Button.getIdForCc(CcNumberOrNote);
		return id;
	}

	public static Encoder getEncoderForParameterClass(
			Class<? extends ProgramParameter> programParameterClass) {
		for (Encoder encoder : BankLayout.CurrentBank.encoders) {
			final Class<? extends ProgramParameter> classCandidate = encoder
					.getProgramParameter().getClass();
			if (classCandidate.equals(programParameterClass))
				return encoder;
		}
		throw new EventProcessorException(
				"Could not find encoder for parameter class: "
						+ programParameterClass.getSimpleName());
	}

	public static int getCcNumberAssociatedToaRelativeEncoder() {
		int cc = 1;
		for (EventProcessorMap map : MapRepository.maps) {
			for (Controller controller : map.getControllers()) {
				if (Mode.Absolute == controller.getMode()
						|| !(controller instanceof Encoder))
					continue;
				System.out.println("has picked " + controller);
				return controller.getCCOrNoteNumber();
			}
		}
		return cc;
	}

}
