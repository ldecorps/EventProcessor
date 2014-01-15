package decorps.eventprocessor.exceptions;

@SuppressWarnings("serial")
public class WrongSysexPayloadSize extends RuntimeException {

	public WrongSysexPayloadSize(String message) {
		super(message);
	}

}
