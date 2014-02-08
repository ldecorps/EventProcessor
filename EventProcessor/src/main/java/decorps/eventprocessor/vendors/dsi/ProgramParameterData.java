package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import decorps.eventprocessor.vendors.dsi.programparameters.FilterAudioMod;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterConfigMode;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterCutoffFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvAttack;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvDecay;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvDelay;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvRelease;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvSustain;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterEnvVelocity;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterKeyboardAmt;
import decorps.eventprocessor.vendors.dsi.programparameters.FilterResonance;
import decorps.eventprocessor.vendors.dsi.programparameters.GlideMode;
import decorps.eventprocessor.vendors.dsi.programparameters.NoiseLevel;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc1KeyTrack;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2FineFreq;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Osc2KeyTrack;
import decorps.eventprocessor.vendors.dsi.programparameters.OscHardSync;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Glide;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator2Glide;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator2Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.OscillatorMix;
import decorps.eventprocessor.vendors.dsi.programparameters.OscillatorSlop;
import decorps.eventprocessor.vendors.dsi.programparameters.PanSpread;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramVolume;
import decorps.eventprocessor.vendors.livid.BankLayout;

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

	ProgramParameterData(byte[] data) {
		super();
		this.data = data;
		this.Name = bytesToText(Arrays.copyOfRange(data, 184, 200));
		Class<? extends ProgramParameter>[] parameters = getProgramParameters();
		A = buildA(parameters);
		B = buildB(parameters);
		CurrentLayer = A;
		layers = new Layer[] { A, B };
		BankLayout.programParameterData = this;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends ProgramParameter>[] getProgramParameters() {
		Class<? extends ProgramParameter>[] parameters = new Class[] {
				Osc1Frequency.class, Osc1FineFreq.class,
				Oscillator1Shape.class, Oscillator1Glide.class,
				Osc1KeyTrack.class, Osc2Frequency.class, Osc2FineFreq.class,
				Oscillator2Shape.class, Oscillator2Glide.class,
				Osc2KeyTrack.class, OscHardSync.class, GlideMode.class,
				OscillatorSlop.class, OscillatorMix.class, NoiseLevel.class,
				FilterCutoffFreq.class, FilterResonance.class,
				FilterKeyboardAmt.class, FilterAudioMod.class,
				FilterConfigMode.class, FilterEnvAmount.class,
				FilterEnvVelocity.class, FilterEnvDelay.class,
				FilterEnvAttack.class, FilterEnvDecay.class,
				FilterEnvSustain.class, FilterEnvRelease.class, VCALevel.class,
				PanSpread.class, ProgramVolume.class, VCAEnvAmount.class,
				VCAEnvVelocity.class, VCAEnvDelay.class, VCAEnvAttack.class,
				VCAEnvDecay.class, VCAEnvSustain.class, VCAEnvRelease.class };
		return parameters;
	}

	private Layer buildB(Class<? extends ProgramParameter>... parametersClasses) {
		return build(offset + 200, parametersClasses);
	}

	private Layer buildA(Class<? extends ProgramParameter>... parametersClasses) {
		return build(offset, parametersClasses);
	}

	public Layer build(int offset,
			Class<? extends ProgramParameter>... parametersClasses) {
		return Layer.build(parametersClasses, data, offset);
	}

	public List<ProgramParameter> getAll400AbstractProgramParameters() {
		List<ProgramParameter> result = new ArrayList<ProgramParameter>(400);
		for (int i = 0; i < 2; i++) {
			populateProgramParameterForLayer(result, i);
		}
		return result;
	}

	public List<ProgramParameter> getFirst200AbstractProgramParameters() {
		List<ProgramParameter> result = new ArrayList<ProgramParameter>(200);
		populateProgramParameterForLayer(result, 0);
		return result;
	}

	public void populateProgramParameterForLayer(List<ProgramParameter> result,
			int i) {
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
		result.add(layers[i].oscillatorHardSync);
		result.add(layers[i].glideMode);
		result.add(layers[i].oscillatorSlop);
		result.add(layers[i].oscillatorMix);
		result.add(layers[i].noiseLevel);
		result.add(layers[i].filterCutoffFreq);
		result.add(layers[i].filterResonance);
		result.add(layers[i].filterKeyboardAmt);
		result.add(layers[i].filterAudioMod);
		result.add(layers[i].filterConfigMode);
		result.add(layers[i].filterEnvAmount);
		result.add(layers[i].filterEnvVelocity);
		result.add(layers[i].filterEnvDelay);
		result.add(layers[i].filterEnvAttack);
		result.add(layers[i].filterEnvDecay);
		result.add(layers[i].filterEnvSustain);
		result.add(layers[i].filterEnvRelease);
		result.add(layers[i].vcaLevel);
		result.add(layers[i].panSpread);
		result.add(layers[i].programVolume);
		result.add(layers[i].vcaEnvAmount);
		result.add(layers[i].vcaEnvVelocity);
		result.add(layers[i].vcaEnvDelay);
		result.add(layers[i].vcaEnvAttack);
		result.add(layers[i].vcaEnvDecay);
		result.add(layers[i].vcaEnvSustain);
		result.add(layers[i].vcaEnvRelease);

	}

	public Layer currentLayer() {
		return CurrentLayer;
	}

}
