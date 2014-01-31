package decorps.eventprocessor.vendors.dsi.messages;

import static decorps.eventprocessor.utils.BaseUtils.binaryToByte;

import javax.sound.midi.ShortMessage;

import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorMidiMessageComposite;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class EventProcessorNRPNMessage extends
		EventProcessorMidiMessageComposite {

	public final int NRPNControllerNumber;
	public final byte NRPNControllerValue;
	public static final byte NRPN_parameter_number_MSB_CC = binaryToByte("0110 0011");
	public static final byte NRPN_parameter_number_LSB_CC = binaryToByte("0110 0010");
	public static final byte NRPN_parameter_value_MSB_CC = binaryToByte("0000 0110");
	public static final byte NRPN_parameter_value_LSB_CC = binaryToByte("0010 0110");

	private EventProcessorNRPNMessage(int position, byte controllerValue) {
		this.NRPNControllerNumber = position;
		this.NRPNControllerValue = controllerValue;
		eventProcessorMidiMessages.add(EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE,
						NRPN_parameter_number_MSB_CC,
						get7MostSignificantBits(position)));
		eventProcessorMidiMessages.add(EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE,
						NRPN_parameter_number_LSB_CC,
						get7LeastSignificantBits(position)));
		eventProcessorMidiMessages.add(EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE,
						NRPN_parameter_value_MSB_CC,
						get7MostSignificantBits(controllerValue)));
		eventProcessorMidiMessages.add(EventProcessorShortMessage
				.buildShortMessage(ShortMessage.CONTROL_CHANGE,
						NRPN_parameter_value_LSB_CC,
						get7LeastSignificantBits(controllerValue)));
	}

	private byte get7MostSignificantBits(int m) {
		byte result = (byte) ((m & 1 << 7) >> 7);
		return result;
	}

	private byte get7LeastSignificantBits(int m) {
		byte result = (byte) (m & ((1 << 7) - 1));
		return result;
	}

	public static EventProcessorMidiMessage buildEventProcessorNRPNMessage(
			int controllerNumber, int controllerValue) {
		return new EventProcessorNRPNMessage((byte) controllerNumber,
				(byte) controllerValue);
	}

	public static EventProcessorMidiMessage buildEventProcessorNRPNMessage(
			AbstractProgramParameter abstractProgramParameter) {
		return new EventProcessorNRPNMessage(
				abstractProgramParameter.getLayerANRPNNumber(),
				abstractProgramParameter.data);
	}

	@Override
	public byte[] getMessage() {
		byte[] result = new byte[8];
		for (int i = 0; i < eventProcessorMidiMessages.size(); i++) {
			result[i * 2] = (byte) eventProcessorMidiMessages.get(i)
					.getAsShortMessage().getData1();
			result[i * 2 + 1] = (byte) eventProcessorMidiMessages.get(i)
					.getAsShortMessage().getData2();
		}
		return result;
	}

}
