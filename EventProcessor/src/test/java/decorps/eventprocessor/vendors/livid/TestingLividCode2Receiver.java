package decorps.eventprocessor.vendors.livid;

import java.io.PrintStream;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import decorps.eventprocessor.utils.DumpReceiver;

public class TestingLividCode2Receiver extends DumpReceiver implements Receiver {

	public TestingLividCode2Receiver() {
		this(System.out);
	}

	public TestingLividCode2Receiver(PrintStream printStream) {
		super(printStream);
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		super.send(message, timeStamp);
	}

	@Override
	public void close() {
		super.close();
	}

}
