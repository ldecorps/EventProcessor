package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import decorps.eventprocessor.vendors.dsi.programparameters.BreathAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.BreathDestination;
import decorps.eventprocessor.vendors.dsi.programparameters.Env3Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Attack;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Decay;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Delay;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Release;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Sustain;
import decorps.eventprocessor.vendors.dsi.programparameters.Envelope3Velocity;
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
import decorps.eventprocessor.vendors.dsi.programparameters.FootControlAmt;
import decorps.eventprocessor.vendors.dsi.programparameters.FootControlDest;
import decorps.eventprocessor.vendors.dsi.programparameters.GlideMode;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1KeySync;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO1Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO2Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO2Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO2Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO2KeySync;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO2Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO3Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO3Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO3Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO3KeySync;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO3Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO4Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO4Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO4Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO4KeySync;
import decorps.eventprocessor.vendors.dsi.programparameters.LFO4Shape;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod1Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod1Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod1Source;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod2Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod2Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod2Source;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod3Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod3Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod3Source;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod4Amount;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod4Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Mod4Source;
import decorps.eventprocessor.vendors.dsi.programparameters.ModWheelAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.ModWheelDest;
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
import decorps.eventprocessor.vendors.dsi.programparameters.PressureAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.PressureDestination;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramVolume;
import decorps.eventprocessor.vendors.dsi.programparameters.Seq1Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Seq2Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Seq3Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.Seq4Destination;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvAttack;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvDecay;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvDelay;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvRelease;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvSustain;
import decorps.eventprocessor.vendors.dsi.programparameters.VCAEnvVelocity;
import decorps.eventprocessor.vendors.dsi.programparameters.VCALevel;
import decorps.eventprocessor.vendors.dsi.programparameters.VelocityAmount;
import decorps.eventprocessor.vendors.dsi.programparameters.VelocityDestination;
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
				VCAEnvDecay.class, VCAEnvSustain.class, VCAEnvRelease.class,
				LFO1Frequency.class, LFO1Shape.class, LFO1Amount.class,
				LFO1Destination.class, LFO1KeySync.class, LFO2Frequency.class,
				LFO2Shape.class, LFO2Amount.class, LFO2Destination.class,
				LFO2KeySync.class, LFO3Frequency.class, LFO3Shape.class,
				LFO3Amount.class, LFO3Destination.class, LFO3KeySync.class,
				LFO4Frequency.class, LFO4Shape.class, LFO4Amount.class,
				LFO4Destination.class, LFO4KeySync.class,
				Env3Destination.class, Envelope3Amount.class,
				Envelope3Velocity.class, Envelope3Delay.class,
				Envelope3Attack.class, Envelope3Decay.class,
				Envelope3Sustain.class, Envelope3Release.class,
				Mod1Source.class, Mod1Amount.class, Mod1Destination.class,
				Mod2Source.class, Mod2Amount.class, Mod2Destination.class,
				Mod3Source.class, Mod3Amount.class, Mod3Destination.class,
				Mod4Source.class, Mod4Amount.class, Mod4Destination.class,
				Seq1Destination.class, Seq2Destination.class,
				Seq3Destination.class, Seq4Destination.class,
				ModWheelAmount.class, ModWheelDest.class, PressureAmount.class,
				PressureDestination.class, BreathAmount.class,
				BreathDestination.class, VelocityAmount.class,
				VelocityDestination.class, FootControlAmt.class,
				FootControlDest.class };

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
		result.add(layers[i].lfo1frequency);
		result.add(layers[i].lfo1Shape);
		result.add(layers[i].lfo1Amount);
		result.add(layers[i].lfo1Destination);
		result.add(layers[i].lfo1KeySync);
		result.add(layers[i].lfo2frequency);
		result.add(layers[i].lfo2Shape);
		result.add(layers[i].lfo2Amount);
		result.add(layers[i].lfo2Destination);
		result.add(layers[i].lfo2KeySync);
		result.add(layers[i].lfo3frequency);
		result.add(layers[i].lfo3Shape);
		result.add(layers[i].lfo3Amount);
		result.add(layers[i].lfo3Destination);
		result.add(layers[i].lfo3KeySync);
		result.add(layers[i].lfo4frequency);
		result.add(layers[i].lfo4Shape);
		result.add(layers[i].lfo4Amount);
		result.add(layers[i].lfo4Destination);
		result.add(layers[i].lfo4KeySync);
		result.add(layers[i].env3Destination);
		result.add(layers[i].envelope3Amount);
		result.add(layers[i].envelope3Velocity);
		result.add(layers[i].envelope3Delay);
		result.add(layers[i].envelope3Attack);
		result.add(layers[i].envelope3Decay);
		result.add(layers[i].envelope3Sustain);
		result.add(layers[i].envelope3Release);
		result.add(layers[i].mod1Source);
		result.add(layers[i].mod1Amount);
		result.add(layers[i].mod1Destination);
		result.add(layers[i].mod2Source);
		result.add(layers[i].mod2Amount);
		result.add(layers[i].mod2Destination);
		result.add(layers[i].mod3Source);
		result.add(layers[i].mod3Amount);
		result.add(layers[i].mod3Destination);
		result.add(layers[i].mod4Source);
		result.add(layers[i].mod4Amount);
		result.add(layers[i].mod4Destination);
		result.add(layers[i].seq1Destination);
		result.add(layers[i].seq2Destination);
		result.add(layers[i].seq3Destination);
		result.add(layers[i].seq4Destination);
		result.add(layers[i].modWheelAmount);
		result.add(layers[i].modWheelDest);
		result.add(layers[i].pressureAmount);
		result.add(layers[i].pressureDestination);
		result.add(layers[i].breathAmount);
		result.add(layers[i].breathDestination);
		result.add(layers[i].velocityAmount);
		result.add(layers[i].velocityDestination);
		result.add(layers[i].footControlAmt);
		result.add(layers[i].footControlDest);

	}

	public Layer currentLayer() {
		return CurrentLayer;
	}

}
