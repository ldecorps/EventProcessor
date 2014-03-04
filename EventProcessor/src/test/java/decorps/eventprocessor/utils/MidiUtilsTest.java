package decorps.eventprocessor.utils;

import static decorps.eventprocessor.utils.MidiUtils.buildSysexMessage;
import static decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MidiUtilsTest {
	MidiUtils cut = new MidiUtils();

	@Test
	public void canBuildAndInitialiseSysexData() throws Exception {
		assertEquals(buildSysexMessage(3)[0], System_Exclusive);
		assertEquals(buildSysexMessage(3)[1], 0);
		assertEquals(buildSysexMessage(3)[2], End_Of_Exclusive);
	}

}
