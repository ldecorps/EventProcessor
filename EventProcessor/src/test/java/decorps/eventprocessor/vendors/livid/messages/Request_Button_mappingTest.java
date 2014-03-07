package decorps.eventprocessor.vendors.livid.messages;

import static decorps.eventprocessor.utils.BaseUtils.hexasToBytes;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class Request_Button_mappingTest {
	@Test
	public void create() throws Exception {
		assertArrayEquals(hexasToBytes("F0 00 01 61 04 07 0B F7"),
				LividMessageFactory.build_Request_Button_mapping().getMessage());
	}
}
