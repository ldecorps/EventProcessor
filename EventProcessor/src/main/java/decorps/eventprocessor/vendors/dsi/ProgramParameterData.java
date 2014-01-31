package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.GlideMode;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1KeyTrack;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Glide;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator2Glide;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator2Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.OscillatorMix;
import decorps.eventprocessor.vendors.dsi.programparameters.OscillatorSlop;

public class ProgramParameterData {

	public final String Name;
	public final byte[] data;
	public final Layer A;
	public final Layer B;
	public final Layer[] layers;
	public static Layer CurrentLayer;
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
		this.Name = bytesToText(Arrays.copyOfRange(data, 184, 199));
		Class<? extends AbstractProgramParameter>[] parameters = new Class[] {
				Osc1Frequency.class, Osc1FineFreq.class,
				Oscillator1Shape.class, Oscillator1Glide.class,
				Osc1KeyTrack.class, Osc2Frequency.class, Osc2FineFreq.class,
				Oscillator2Shape.class, Oscillator2Glide.class,
				Osc1KeyTrack.class, Sync.class, GlideMode.class,
				OscillatorSlop.class, OscillatorMix.class };
		A = buildA(parameters);
		B = buildB(parameters);
		CurrentLayer = A;
		layers = new Layer[] { A, B };
	}

	@SuppressWarnings("unchecked")
	private Layer buildB(
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return build(offset + 200, parametersClasses);
	}

	@SuppressWarnings("unchecked")
	private Layer buildA(
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return build(offset, parametersClasses);
	}

	@SuppressWarnings("unchecked")
	public Layer build(int offset,
			Class<? extends AbstractProgramParameter>... parametersClasses) {
		return Layer.build(parametersClasses, data, offset);
	}

	public List<AbstractProgramParameter> getAll400AbstractProgramParameters() {
		List<AbstractProgramParameter> result = new ArrayList<AbstractProgramParameter>(
				400);
		for (int i = 0; i < 2; i++) {
			populateProgramParameterForLayer(result, i);
		}
		return result;
	}

	public List<AbstractProgramParameter> getFirst200AbstractProgramParameters() {
		List<AbstractProgramParameter> result = new ArrayList<AbstractProgramParameter>(
				200);
		populateProgramParameterForLayer(result, 0);
		return result;
	}

	public void populateProgramParameterForLayer(
			List<AbstractProgramParameter> result, int i) {
		result.add(layers[i].oscillator1Frequency);
		result.add(layers[i].oscillator1FineTune);
		result.add(layers[i].oscillator1Shape);
		result.add(layers[i].oscillator1Glide);
		result.add(layers[i].oscillator1Keyboard);
		result.add(layers[i].oscillator2Frequency);
		result.add(layers[i].oscillator2FineTune);
		result.add(layers[i].oscillator2Shape);
		result.add(layers[i].oscillator2Glide);
		result.add(layers[i].oscillator2Keyboard);
	}

	public Layer currentLayer() {
		return CurrentLayer;
	}
}
