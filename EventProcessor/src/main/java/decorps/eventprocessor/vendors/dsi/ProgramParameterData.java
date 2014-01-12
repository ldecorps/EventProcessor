package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.Arrays;

import decorps.eventprocessor.vendors.dsi.ProgramParameters.Oscillator1Frequency;

public class ProgramParameterData {
	@Override
	public String toString() {
		return "ProgramParameterData [Name=" + Name + ", Oscillator1Frequency="
				+ Oscillator1Frequency + "]";
	}

	public final String Name;
	public final byte[] data;
	public final Oscillator1Frequency Oscillator1Frequency;

	public static ProgramParameterData build(byte[] unpacked) {
		return new ProgramParameterData(unpacked);
	}

	public ProgramParameterData(byte[] data) {
		super();
		this.data = data;
		this.Name = bytesToText(Arrays.copyOfRange(data, 184, 200));
		int i = 0;
		Oscillator1Frequency = new Oscillator1Frequency(data[i++]);
	}

}
