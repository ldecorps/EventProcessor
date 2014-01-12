package decorps.eventprocessor.vendors.dsi;

import java.util.Arrays;

import decorps.eventprocessor.utils.BaseUtils;

public class ProgramParameterData {
	public final String Name;
	public final byte[] data;

	public static ProgramParameterData build(byte[] unpacked) {
		return new ProgramParameterData(unpacked);
	}

	public ProgramParameterData(byte[] data) {
		super();
		this.data = data;
		this.Name = BaseUtils.bytesToText(Arrays.copyOfRange(data, 184, 200));
	}
}
