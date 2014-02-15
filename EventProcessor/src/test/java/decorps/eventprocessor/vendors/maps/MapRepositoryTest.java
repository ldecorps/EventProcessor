package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerTest;

public class MapRepositoryTest {
	static {
		BankLayout.programParameterData = ProgramParameterDataTest.sampleProgramParameterData;
	}

	MapRepository cut = new MapRepository();

	@Before
	public void initialiseBank() {
		BankLayout.CurrentBank.initialiseControllers();
		MapRepository.completeInitialisationWithDefaultMaps();
	}

	@Test
	public void newRepository_contains32DefaultMaps() throws Exception {
		MapRepository.maps.clear();

		MapRepository.completeInitialisationWithDefaultMaps();
		final Iterator<EventProcessorMap> iterator = MapRepository.maps
				.iterator();
		int numberOfParametersMapped = Math.min(32,
				ProgramParameterData.getProgramParameters().length);
		assertEquals(numberOfParametersMapped, MapRepository.maps.size());

		for (int i = 0; i < numberOfParametersMapped; i++)
			assertEquals(DefaultControllerParameterMap.class, iterator.next()
					.getClass());
	}

	@Test
	public void canRegisterMaps() throws Exception {
		EventProcessorMap map = new DefaultControllerParameterMap(
				ProgramParameterTest.newSampleRelativeParameter(),
				ControllerTest.newAbsoluteEncoderController());
		assertTrue(MapRepository.contains(map));
	}

	@Test
	public void currentBankIsInitialiseWithRemainingProgramParametersInOrder()
			throws Exception {
		MapRepository.maps.clear();
		ProgramParameter programParameter = MapRepository
				.nextParameterNotMapped();
		Controller controller = MapRepository.nextControllerNotMapped();

		DefaultControllerParameterMap map = MapRepository
				.createMapForNextAvailableParameterAndNextAvailableController();

		assertSame(programParameter, map.getProgramParameter());
		assertTrue(map.contains(controller));
	}

	@Test
	public void test_nextParameterNotMapped() throws Exception {
		MapRepository.maps.clear();
		assertTrue(MapRepository.nextParameterNotMapped().getLayerANRPNNumber() > ProgramParameter.nullParameter
				.getLayerANRPNNumber());
	}

	@Test
	public void canFindNextParameterNotMapped() throws Exception {
		MapRepository.maps.clear();
		ProgramParameter parameterNotMapped = MapRepository
				.nextParameterNotMapped();
		for (EventProcessorMap map : MapRepository.maps) {
			for (Controller controller : map.getControllers())
				assertNotSame(parameterNotMapped,
						controller.getProgramParameter());
		}
	}

	@Test
	public void givenControllerAssignedToParameter_WhenReassignedToNewParameter_IsReRegistered()
			throws Exception {
		new DefaultControllerParameterMap(
				BankLayout.getCurrentProgramParameterData().A.oscillator1Shape,
				BankLayout.CurrentBank.buttons[0]);

		new DefaultControllerParameterMap(
				BankLayout.getCurrentProgramParameterData().A.oscillator2Shape,
				BankLayout.CurrentBank.buttons[0]);

		assertThat(
				MapRepository
						.getParameterForController(BankLayout.CurrentBank.buttons[0]),
				sameInstance(BankLayout.getCurrentProgramParameterData().A.oscillator2Shape));

	}
}
