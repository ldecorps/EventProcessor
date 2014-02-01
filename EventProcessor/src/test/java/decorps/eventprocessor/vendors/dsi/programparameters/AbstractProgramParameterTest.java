package decorps.eventprocessor.vendors.dsi.programparameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Random;

import org.junit.Test;

public class AbstractProgramParameterTest {

	private static final Random random = new Random();

	@Test
	public void canConvertValuesForZeroTo120() throws Exception {
		checkZeroTo120Range(ZeroTo120Range.class, 120, 127);
		checkZeroTo120Range(ZeroTo120Range.class, 0, 0);
	}

	public static AbstractProgramParameter newSampleAbsoluteParameter() {
		return new Oscillator1Glide(0, getRandomByte());
	}

	public static byte getRandomByte() {
		byte[] bytes = new byte[1];
		random.nextBytes(bytes);
		final byte result = (byte) (bytes[0] & 0x7F);
		return result;
	}

	@Test
	public void canConvertValuesForZeroOrOne() throws Exception {
		checkZeroOrOne(0, 0);
		checkZeroOrOne(1, 127);
	}

	public void checkZeroOrOne(int data, int rebased) {
		Osc1KeyTrack oneZeroOrOneParameter = new Osc1KeyTrack(4, (byte) data);
		assertThat(ZeroOrOneRange.class.isAssignableFrom(oneZeroOrOneParameter
				.getClass()), is(true));
		assertThat((int) oneZeroOrOneParameter.getRebasedValue(), is(rebased));
	}

	private void checkZeroTo120Range(Class<? extends HasCcValue> class1,
			int fromTetra, int forLividCode) {
		Osc1Frequency oneZeroTo120Parameter = new Osc1Frequency(0,
				(byte) fromTetra);
		assertThat(class1.isAssignableFrom(oneZeroTo120Parameter.getClass()),
				is(true));
		assertThat((int) oneZeroTo120Parameter.getRebasedValue(),
				is(forLividCode));
	}

	public static AbstractProgramParameter newSampleRelativeParameter() {
		return new Oscillator1Shape(0, getRandomByte());
	}

	public static byte getRandomByteOtherThan(byte rebasedValue) {
		byte candidateValue = 0;
		do {
			candidateValue = getRandomByte();
		} while (rebasedValue == candidateValue);
		return candidateValue;
	}
}
