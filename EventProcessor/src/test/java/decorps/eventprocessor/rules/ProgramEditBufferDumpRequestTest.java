package decorps.eventprocessor.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import decorps.eventprocessor.utils.BaseUtils;

public class ProgramEditBufferDumpRequestTest {
	final ProgramEditBufferDumpRequest programEditBufferDumpRequest = ProgramEditBufferDumpRequest
			.build();

	@Test
	public void canBuildProgramEditBufferDumpRequest() {
		assertThat(programEditBufferDumpRequest.transform(null), notNullValue());
	}

	@Test
	public void willReactUpondSeingaProgramChange() throws Exception {
		byte[] programChange = new byte[] { -112, 64, 127 };
		System.out.println(BaseUtils.bytesToBinary(programChange));
	}

}
