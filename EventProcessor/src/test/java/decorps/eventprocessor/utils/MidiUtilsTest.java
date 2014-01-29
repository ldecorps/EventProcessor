package decorps.eventprocessor.utils;

import static decorps.eventprocessor.utils.MidiUtils.buildSysexMessage;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class MidiUtilsTest {
	MidiUtils cut = new MidiUtils();

	@Test
	@Ignore
	// FIXME why is this suddenly failing
	public void canListMidiDevices() throws Exception {
		List<String> listMidiDevices = cut.listMidiDevices();
		System.out.println(listMidiDevices);
		assertThat(listMidiDevices, hasSize(greaterThan(0)));
	}

	@Test
	public void canBuildAndInitialiseSysexData() throws Exception {
		assertEquals(buildSysexMessage(3)[0], System_Exclusive);
		assertEquals(buildSysexMessage(3)[1], 0);
		assertEquals(buildSysexMessage(3)[2], End_Of_Exclusive);
	}

}
