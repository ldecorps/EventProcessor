package decorps.eventprocessor;

import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class DummyTransmitter implements Transmitter {
	private Receiver receiver;

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void close() {
		if (null != receiver)
			receiver.close();
		receiver = null;
	}

}
