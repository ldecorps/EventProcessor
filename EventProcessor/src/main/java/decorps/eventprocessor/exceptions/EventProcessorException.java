package decorps.eventprocessor.exceptions;

@SuppressWarnings("serial")
public class EventProcessorException extends RuntimeException {

	public EventProcessorException(Throwable e) {
		super(e);
	}

	public EventProcessorException(String message, Throwable e) {
		super(message, e);
	}

	public EventProcessorException(String message) {
		super(message);
	}

}
