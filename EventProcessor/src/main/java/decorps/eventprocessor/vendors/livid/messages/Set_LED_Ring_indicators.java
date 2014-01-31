package decorps.eventprocessor.vendors.livid.messages;

public class Set_LED_Ring_indicators extends AbstractLividCode2SysexMessage {

	public final static boolean ABSOLUTE = false;
	public final static boolean RELATIVE = true;

	/**
	 * 
	 * This command sets the Mode for each encoder as "absolute" or "inc/dec".
	 * Absolute mode behaves as a linear poteniometer sweeping from 0-127.
	 * Inc/dec will send a value of 127 on a CW turn, and a value of 1 on a CCW
	 * turn. E1: Acceptable Values: 0-7F. Setting the individual bits for this
	 * byte controls mode for Encoders 0-6. A '0' for a bit sets the mode to
	 * "absolute," a '1' sets the mode to "inc/dec." E2: Acceptable Values: 0-7F
	 * Setting the individual bits for this byte controls mode for Encoder 7.
	 * Any byte value greater than 0 will set Encoder 7 to "inc/dec" mode. E3:
	 * controls Encoders 8-14 E4: controls Encoder 15 and so on.
	 */
	public Set_LED_Ring_indicators(int[] data, int specificByte,
			int maximumLength) {
		super(data, specificByte, maximumLength);
	}

}
