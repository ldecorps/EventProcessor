package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.EventProcessorTest;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerTest;

public class DefaultControllerParameterMapTest {

	@Before
	public void initialise() {
		EventProcessorTest.getInstanceWithoutActions().initialise();
	}

	@Test
	public void defaultMap_IsOneProgramParameterToOneControllers()
			throws Exception {
		ProgramParameter oneParam = ProgramParameterTest
				.newSampleAbsoluteParameter();
		Controller oneController = ControllerTest
				.newAbsoluteEncoderController();
		Controller anotherController = ControllerTest
				.newAbsoluteEncoderController();
		new DefaultControllerParameterMap(oneParam, oneController,
				anotherController);

		final byte newValue = ProgramParameterTest
				.getRandomByteOtherThan(oneController.getRebasedValue());
		assertThat(oneController.getRebasedValue(), not(newValue));

		oneParam.setValue(oneController, newValue);

		assertEquals(newValue, oneController.getRebasedValue());
		// assertThat(anotherController.getRebasedValue(), not(newValue));
	}

}
