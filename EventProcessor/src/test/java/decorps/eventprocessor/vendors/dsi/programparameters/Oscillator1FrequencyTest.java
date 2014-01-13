package decorps.eventprocessor.vendors.dsi.programparameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.typeCompatibleWith;

import org.junit.Test;

public class Oscillator1FrequencyTest {
	@Test
	public void hasRangeZeroTo120() throws Exception {

		assertThat(Oscillator1Frequency.class,
				typeCompatibleWith(ZeroTo120Range.class));
	}
}
