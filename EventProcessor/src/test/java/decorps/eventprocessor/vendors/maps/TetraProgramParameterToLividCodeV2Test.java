package decorps.eventprocessor.vendors.maps;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new TetraProgramParameterToLividCodeV2();

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;

	@Before
	public void initialiseLayout() {
		BankLayout.CurrentBank = new BankLayout(1);
	}

	private void checkOscillator1Frequency(EventProcessorMidiMessage forCodeV2) {
		assertEquals("channel", 0, forCodeV2.getAsShortMessage().getChannel());
		assertEquals("ledring", 1, forCodeV2.getAsShortMessage().getData1());
		assertEquals("value", 25, forCodeV2.getAsShortMessage().getData2());
	}

	@Test
	public void canMapWholeProgramParameterData() throws Exception {
		EventProcessorMidiMessage allCcShortMessages = cut
				.mapToCcs(sampleProgramParameterData);
		checkOscillator1Frequency(allCcShortMessages.getAsComposite()
				.getMessages().get(0));
	}

	public TetraProgramParameterToLividCodeV2 getCutAsTetraToLividMap() {
		return (TetraProgramParameterToLividCodeV2) cut;
	}

	@Test
	@Ignore
	// TODO the tetra params are all sent down, using the all the banks,
	// that means usage of change program to populate the 4 banks
	public void tetraParamsShouldBeMappedToAllBanks_EverythingIsSentDown()
			throws Exception {
		throw new EventProcessorException("Not Implemented Yet");
	}
}
