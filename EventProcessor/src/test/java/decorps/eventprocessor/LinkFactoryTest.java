package decorps.eventprocessor;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LinkFactoryTest {
	@Test
	public void canConnectToLpk2() throws Exception {
		LinkFactory linkFactory = new LinkFactory(null);
		if (LinkFactory.isAkaiLpk25PluggedIn())
			assertThat(linkFactory.tryToGetKeyboardOrDefaultDummyTransmitter(),
					not(instanceOf(DummyTransmitter.class)));
		else
			assertThat(linkFactory.tryToGetKeyboardOrDefaultDummyTransmitter(),
					instanceOf(DummyTransmitter.class));
	}
}
