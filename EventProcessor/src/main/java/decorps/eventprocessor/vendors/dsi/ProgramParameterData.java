package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1FineTune;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Glide;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Keyboard;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;

public class ProgramParameterData {

	public final String Name;
	public final byte[] data;
	public final Layer A;
	public final Layer B;
	public final Layer[] layers;
	Layer currentLayer;
	int offset = 0;

	@Override
	public String toString() {
		return "ProgramParameterData [Name=" + Name + ", A=" + A + ", B=" + B
				+ "]";
	}

	public static ProgramParameterData build(byte[] unpacked) {
		return new ProgramParameterData(unpacked);
	}

	@SuppressWarnings("unchecked")
	ProgramParameterData(byte[] data) {
		super();
		this.data = data;
		this.Name = bytesToText(Arrays.copyOfRange(data, 184, 200));
		Class<? extends AbstractProgramParameter>[] parameters = new Class[] {
				Oscillator1Frequency.class, Oscillator1FineTune.class,
				Oscillator1Shape.class, Oscillator1Glide.class,
				Oscillator1Keyboard.class };
		A = buildA(parameters);
		B = buildB(parameters);
		currentLayer = A;
		layers = new Layer[] { A, B };
	}

	private Layer buildB(
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return build(offset + 200, parametersClasses);
	}

	private Layer buildA(
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return build(offset, parametersClasses);
	}

	public Layer build(int offset,
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return Layer.build(parametersClasses, data, offset);
	}

	public List<AbstractProgramParameter> getAllAbstractProgramParameters() {
		List<AbstractProgramParameter> result = new ArrayList<AbstractProgramParameter>(
				400);
		for (int i = 0; i < 2; i++) {
			populateProgramParameterForLayer(result, i);
		}
		return result;
	}

	public void populateProgramParameterForLayer(
			List<AbstractProgramParameter> result, int i) {
		result.add(layers[i].oscillator1Frequency);
		result.add(layers[i].oscillator1FineTune);
		result.add(layers[i].oscillator1Shape);
		result.add(layers[i].oscillator1Glide);
		result.add(layers[i].oscillator1Keyboard);
	}

	public Layer currentLayer() {
		return currentLayer;
	}
}
