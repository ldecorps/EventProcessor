package decorps.eventprocessor;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.MidiSystem;

import org.junit.Test;

public class LinkFactoryTest {
	@Test
	public void canConnectToLpk2() throws Exception {
		LinkFactory linkFactory = new LinkFactory(null);
		if (LinkFactory.isAkaiLpk25PluggedIn())
			assertThat(linkFactory.tryToGetKeyboardOrDefaultDummyTransmitter(),
					not(instanceOf(DummyTransmitter.class)));
		else if (!LinkFactory.isKorgMicroKey25PluggedIn())
			assertThat(linkFactory.tryToGetKeyboardOrDefaultDummyTransmitter(),
					instanceOf(DummyTransmitter.class));
	}

	@Test
	public void canConnectToMicrokey25() throws Exception {
		LinkFactory linkFactory = new LinkFactory(null);
		if (LinkFactory.isKorgMicroKey25PluggedIn())
			assertThat(linkFactory.tryToGetKeyboardOrDefaultDummyTransmitter(),
					not(instanceOf(DummyTransmitter.class)));
		else if (!LinkFactory.isAkaiLpk25PluggedIn())
			assertThat(linkFactory.tryToGetKeyboardOrDefaultDummyTransmitter(),
					instanceOf(DummyTransmitter.class));
	}

	@Test
	public void getMidiDeviceInfo_knowsWhenMmjIsWorking() throws Exception {
		assertTrue(LinkFactory.isMmjRunning(MidiSystem.getMidiDeviceInfo()));
	}

}
