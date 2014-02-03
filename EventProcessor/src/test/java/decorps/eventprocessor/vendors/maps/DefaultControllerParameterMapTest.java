package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerTest;

public class DefaultControllerParameterMapTest {
	@Test
	public void DefaultMap_IsOneProgramParameterToOneController()
			throws Exception {
		ProgramParameter oneParam = ProgramParameterTest
				.newSampleAbsoluteParameter();
		Controller oneController = ControllerTest
				.newAbsoluteEncoderController();
		Controller anotherController = ControllerTest
				.newAbsoluteEncoderController();
		EventProcessorMap defaultMap = new DefaultControllerParameterMap(
				oneParam, oneController, anotherController);

		final byte newValue = ProgramParameterTest
				.getRandomByteOtherThan(oneController.getRebasedValue());
		oneParam.setValue(newValue);
		assertThat(oneController.getRebasedValue(), not(newValue));
		defaultMap.refreshControllers();

		assertEquals(newValue, oneController.getRebasedValue());
		assertThat(anotherController.getRebasedValue(), not(newValue));
	}

	@Test
	public void newMap_RegistersItselfWithTheRepository() throws Exception {
		EventProcessorMap map = new Oscillator1Glide_to_E0B1(
				ProgramParameterTest.newSampleAbsoluteParameter(),
				ControllerTest.newAbsoluteEncoderController());

		assertThat(MapRepository.maps, hasItem(map));
	}

}
