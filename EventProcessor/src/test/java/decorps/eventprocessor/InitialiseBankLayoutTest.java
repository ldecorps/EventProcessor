package decorps.eventprocessor;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.sound.midi.Receiver;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.maps.EventProcessorMap;
import decorps.eventprocessor.vendors.maps.MapRepository;

public class InitialiseBankLayoutTest {
	EventProcessor eventProcessor = EventProcessorTest
			.getInstanceWithoutActions();
	InitialiseBankLayout cut = new InitialiseBankLayout(eventProcessor);

	@Test
	public void initialiseSetsTheCurrentProgramFromTetra() throws Exception {
		Receiver toLivid = spy(eventProcessor.fromTetraToLivid.receiver
				.getRawReceiver());
		doReturn(ProgramParameterDataTest.sampleProgramParameterData.data)
				.when(toLivid);

		assertNotNull(BankLayout.programParameterData);
		assertSame(ProgramParameterDataTest.sampleProgramParameterData,
				BankLayout.programParameterData);
	}

	@Test
	public void allButtonToggles() throws Exception {
		cut.setButtonToggleModeEnable();
	}

	@Test
	public void initialise() throws Exception {
		cut.initialise();
	}

	@Test
	public void initialise_OscFreq1_isSameInstanceInMap() throws Exception {
		cut.initialise();

		List<EventProcessorMap> maps = MapRepository
				.getMapsForController(ControllerRepository.getEncoderById(0));
		assertThat(maps, hasSize(1));
		EventProcessorMap map = maps.get(0);

		assertThat(map.getProgramParameter(), sameInstance(map.getControllers()
				.get(0).getProgramParameter()));
	}

}
