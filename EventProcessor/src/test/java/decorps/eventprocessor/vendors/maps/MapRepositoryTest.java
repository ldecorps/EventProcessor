package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameterTest;
import decorps.eventprocessor.vendors.livid.ControllerTest;

public class MapRepositoryTest {
	@Test
	public void newRepository_contains32DefaultMaps() throws Exception {
		final Iterator<EventProcessorMap> iterator = MapRepository.getMaps()
				.iterator();
		for (int i = 0; i < 32; i++) {
			assertThat(iterator.next(),
					instanceOf(DefaultControllerParameterMap.class));
		}
	}

	@Test
	public void canRegisterMaps() throws Exception {
		EventProcessorMap map = new DefaultControllerParameterMap(
				AbstractProgramParameterTest.newSampleRelativeParameter(),
				ControllerTest.newAbsoluteEncoderController());
		assertTrue(MapRepository.contains(map));
	}

}
