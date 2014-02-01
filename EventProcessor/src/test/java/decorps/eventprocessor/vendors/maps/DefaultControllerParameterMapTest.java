package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameterTest;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerTest;

public class DefaultControllerParameterMapTest {
	@Test
	public void DefaultMap_IsOneProgramParameterToOneController()
			throws Exception {
		AbstractProgramParameter oneParam = AbstractProgramParameterTest
				.newSampleAbsoluteParameter();
		Controller oneController = ControllerTest
				.newAbsoluteEncoderController();
		Controller anotherController = ControllerTest
				.newAbsoluteEncoderController();
		EventProcessorMap defaultMap = new DefaultControllerParameterMap(
				oneParam, oneController, anotherController);

		final byte newValue = AbstractProgramParameterTest.getRandomByte();
		oneParam.setValue(newValue);
		assertThat(oneController.getRebasedValue(), not(newValue));
		defaultMap.refreshControllers();

		assertEquals(oneController.getRebasedValue(), newValue);
	}
}
