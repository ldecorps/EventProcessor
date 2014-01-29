package decorps.eventprocessor.vendors.maps;

import static decorps.eventprocessor.utils.BaseUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.vendors.dsi.ProgramParameterData;
import decorps.eventprocessor.vendors.dsi.ProgramParameterDataTest;
import decorps.eventprocessor.vendors.livid.BankLayout;
import decorps.eventprocessor.vendors.livid.messages.LividMessageFactory;

public class TetraProgramParameterToLividCodeV2Test {
	EventProcessorMap cut = new TetraProgramParameterToLividCodeV2();

	final static ProgramParameterData sampleProgramParameterData = ProgramParameterDataTest.sampleProgramParameterData;

	@Before
	public void initialiseLayout() {
		BankLayout.CurrentBank = new BankLayout();
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

	@Test
	public void understandWhichButtonsNeedToBeTurnedOnOrOff() throws Exception {
		getCutAsTetraToLividMap().mapToSetAllLedIndicators(
				sampleProgramParameterData);
		printOutBytesAsHexa(sampleProgramParameterData.data);
		final String firstSevenIndicatorLEDs = byteToBinary(BankLayout.CurrentBank
				.getButtonsAsByteArrays()[0]);
		assertEquals("Should lit up square shape", "0000 1001",
				firstSevenIndicatorLEDs);
	}

	@Test
	public void mapProgramParameterData_to_SetAllLedIndicators()
			throws Exception {
		EventProcessorMidiMessage result = getCutAsTetraToLividMap()
				.mapToSetAllLedIndicators(sampleProgramParameterData);
		final byte[] message = result.getMessage();
		assertArrayEquals(
				LividMessageFactory.buildSet_all_LED_indicators(
						binaryToByte("0000 1001"), binaryToByte("0000 1000"))
						.getMessage(), message);
	}

	public TetraProgramParameterToLividCodeV2 getCutAsTetraToLividMap() {
		return (TetraProgramParameterToLividCodeV2) cut;
	}

	@Test
	public void mapProgramParametejrData_to_SetLedRingIndicators()
			throws Exception {
		EventProcessorMidiMessage result = getCutAsTetraToLividMap()
				.mapToSetLedRingsIndicators(sampleProgramParameterData);
		assertArrayEquals(LividMessageFactory.buildSet_LED_Ring_indicators(17)
				.getMessage(), result.getMessage());
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
