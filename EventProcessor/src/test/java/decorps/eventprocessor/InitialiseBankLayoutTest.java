package decorps.eventprocessor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class InitialiseBankLayoutTest {
	EventProcessor eventProcessor = EventProcessor.build();
	InitialiseBankLayout cut = new InitialiseBankLayout(eventProcessor);

	@Test
	public void initialiseSetsTheCurrentProgramFromTetra() throws Exception {

		RulesAwareReceiverWrapper receiver = spy(eventProcessor.fromLividToTetra.receiver);
		doReturn(ProgramParameterDataTest.sampleProgramParameterData.data)
				.when(receiver);

		cut.initialise();
		assertNotNull(BankLayout.programParameterData);
		assertSame(ProgramParameterDataTest.sampleProgramParameterData,
				BankLayout.programParameterData);
	}
}
