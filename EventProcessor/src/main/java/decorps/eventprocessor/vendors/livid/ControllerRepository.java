package decorps.eventprocessor.vendors.livid;

import static decorps.eventprocessor.vendors.livid.BankLayout.CurrentBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public static Controller getControllerForCc(int ccNumber) {
		List<Integer> listOfCcs = new ArrayList<Integer>();
		for (Encoder encoder : BankLayout.CurrentBank.encoders) {
			final int ccOrNoteNumber = encoder.getCCOrNoteNumber();
			listOfCcs.add(ccOrNoteNumber);
			if (ccOrNoteNumber == ccNumber)
				return encoder;
		}
		for (Button button : BankLayout.CurrentBank.buttons) {
			final int ccOrNoteNumber = button.getCCOrNoteNumber();
			listOfCcs.add(ccOrNoteNumber);
			if (ccOrNoteNumber == ccNumber)
				return button;
		}
		throw new EventProcessorException(
				"Could not find encoder for cc number " + ccNumber
						+ " on bank " + CurrentBank.bankNumber + ". List of "
						+ listOfCcs.size() + " available Ccs: " + listOfCcs);
	}

	public static Encoder getEncoderForCc(int ccNumber) {
		List<Integer> listOfCcs = new ArrayList<Integer>();
		for (Encoder encoder : BankLayout.CurrentBank.encoders) {
			final int ccOrNoteNumber = encoder.getCCOrNoteNumber();
			listOfCcs.add(ccOrNoteNumber);
			if (ccOrNoteNumber == ccNumber)
				return encoder;
		}
		throw new EventProcessorException(
				"Could not find encoder for cc number " + ccNumber
						+ " on bank " + CurrentBank.bankNumber + ". List of "
						+ listOfCcs.size() + " available Ccs: " + listOfCcs
						+ ". List of encoder ids: "
						+ BankLayout.getListOfEncodersIdsForBank(0));
	}

	public static Button getButtonById(byte buttonId) {
		for (Button button : BankLayout.CurrentBank.buttons) {
			if (buttonId == button.id)
				return button;
		}
		throw new EventProcessorException("Could not find button id "
				+ buttonId);
	}

	public static List<Encoder> getControllers() {
		return Arrays.asList(CurrentBank.encoders);
	}

	public static int getLedRingCcForLividCc(int lividCc) {
		final Encoder encoderForCc = getEncoderForCc(lividCc);
		final int encoderId = encoderForCc.getCCOrNoteNumber();
		final int ledRingCc = encoderId + 32;
		return ledRingCc;
	}

	public static Encoder getEncoderById(int encoderId) {
		for (Encoder encoder : BankLayout.CurrentBank.encoders) {
			if (encoderId == encoder.id)
				return encoder;
		}
		throw new EventProcessorException("Could not find encoder id "
				+ encoderId);
	}
}
