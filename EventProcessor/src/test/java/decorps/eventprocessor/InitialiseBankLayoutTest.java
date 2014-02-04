package decorps.eventprocessor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.sound.midi.Receiver;

import org.junit.Test;

import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class InitialiseBankLayoutTest {
	EventProcessor eventProcessor = EventProcessor.build();
	InitialiseBankLayout cut = new InitialiseBankLayout(eventProcessor);

	@Test
	public void initialiseSetsTheCurrentProgramFromTetra() throws Exception {
		Receiver toLivid = spy(eventProcessor.fromTetraToLivid.receiver
				.getRawReceiver());
		doReturn(ProgramParameterDataTest.sampleProgramParameterData.data)
				.when(toLivid);

		cut.initialise();
		assertNotNull(BankLayout.programParameterData);
		assertSame(ProgramParameterDataTest.sampleProgramParameterData,
				BankLayout.programParameterData);
	}
}
