package decorps.eventprocessor.vendors.maps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import decorps.eventprocessor.EventProcessorTest;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameterTest;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.Controller;
import decorps.eventprocessor.vendors.livid.ControllerRepository;
import decorps.eventprocessor.vendors.livid.ControllerTest;
import decorps.eventprocessor.vendors.livid.Encoder;

public class DefaultControllerParameterMapTest {

	@Before
	public void initialise() {
		EventProcessorTest.getInstanceWithoutActions().initialise();
		BankLayout.CurrentBank.initialiseControllers();
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
	}

	@Test
	public void aMapHasSameInstanceOfParameterInItsControllers()
			throws Exception {
		final ProgramParameter parameter = ProgramParameterTest
				.newSampleAbsoluteParameter();
		final Encoder encoder = ControllerRepository.getEncoderById(1);
		DefaultControllerParameterMap cut = new DefaultControllerParameterMap(
				parameter, encoder);

		assertThat(parameter, sameInstance(cut.getControllers().get(0)
				.getProgramParameter()));
	}

}
