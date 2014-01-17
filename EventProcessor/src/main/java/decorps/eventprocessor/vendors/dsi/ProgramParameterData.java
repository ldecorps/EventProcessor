package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;

public class ProgramParameterData {

	public final String Name;
	public final byte[] data;
	public final Layer A;
	public final Layer B;
	Layer currentLayer;
	int i = 0;

	@Override
	public String toString() {
		return "ProgramParameterData [Name=" + Name + ", A=" + A + ", B=" + B
				+ "]";
	}

	public static ProgramParameterData build(byte[] unpacked) {
		return new ProgramParameterData(unpacked);
	}

	@SuppressWarnings("unchecked")
	public ProgramParameterData(byte[] data) {
		super();
		this.data = data;
		this.Name = bytesToText(Arrays.copyOfRange(data, 184, 200));
		Class<? extends AbstractProgramParameter>[] parameters = new Class[] {
				Oscillator1Frequency.class, null, Oscillator1Shape.class };
		A = buildA(parameters);
		B = buildB(parameters);
		currentLayer = A;
	}

	@SuppressWarnings("unchecked")
	private Layer buildB(
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return build(data[i + 200], parametersClasses);
	}

	@SuppressWarnings("unchecked")
	private Layer buildA(
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return build(data[i], parametersClasses);
	}

	@SuppressWarnings("unchecked")
	public Layer build(byte data,
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return Layer.build(parametersClasses, data);
	}

	public List<AbstractProgramParameter> getAllAbstractProgramParameters() {
		List<AbstractProgramParameter> result = new ArrayList<AbstractProgramParameter>(
				400);
		result.add(A.oscillator1Frequency);
		result.add(B.oscillator1Frequency);
		return result;
	}

	public Layer currentLayer() {
		return currentLayer;
	}
}
