package decorps.eventprocessor.utils;

import static decorps.eventprocessor.utils.MidiUtils.buildSysexMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class MidiUtilsTest {
	MidiUtils cut = new MidiUtils();

	@Test
	public void canListMidiDevices() throws Exception {
		List<String> listMidiDevices = cut.listMidiDevices();
		System.out.println(listMidiDevices);
		assertThat(listMidiDevices, hasSize(greaterThan(0)));
	}

	@Test
	public void canBuildAndInitialiseSysexData() throws Exception {
		assertEquals(buildSysexMessage(3)[0], DsiTetraMap.System_Exclusive);
		assertEquals(buildSysexMessage(3)[1], 0);
		assertEquals(buildSysexMessage(3)[2], DsiTetraMap.End_Of_Exclusive);
	}

}
