package decorps.eventprocessor.utils;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import decorps.eventprocessor.utils.MidiUtils;

public class MidiUtilsTest {
	MidiUtils cut = new MidiUtils();

	@Test
	public void canListMidiDevices() throws Exception {
		List<String> listMidiDevices = cut.listMidiDevices();
		System.out.println(listMidiDevices);
		assertThat(listMidiDevices, hasSize(greaterThan(0)));
	}
}
