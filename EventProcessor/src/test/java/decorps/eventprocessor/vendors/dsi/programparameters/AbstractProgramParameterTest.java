package decorps.eventprocessor.vendors.dsi.programparameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class AbstractProgramParameterTest {
	@Test
	public void canConvertValuesForZeroTo120() throws Exception {
		checkZeroTo120Range(ZeroTo120Range.class, 120, 127);
		checkZeroTo120Range(ZeroTo120Range.class, 0, 0);
	}

	private void checkZeroTo120Range(Class<? extends HasCcValue> class1,
			int fromTetra, int forLividCode) {
		Oscillator1Frequency oneZeroTo120Parameter = new Oscillator1Frequency(
				(byte) fromTetra);
		assertThat(class1.isAssignableFrom(oneZeroTo120Parameter.getClass()),
				is(true));
		assertThat((int) oneZeroTo120Parameter.getRebasedValue(), is(forLividCode));
	}
}
