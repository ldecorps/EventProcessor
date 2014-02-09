package decorps.eventprocessor.vendors.dsi;

import static decorps.eventprocessor.utils.BaseUtils.bytesToText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import decorps.eventprocessor.vendors.dsi.programparameters.*;
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
				FootControlDest.class, ClockBPM.class, ClockDivide.class,
				PitchWheelRange.class, SequencerTrigger.class, KeyMode.class,
				UnissonMode.class, ArpeggiatorMode.class,
				Envelope3Repeat.class, UnissonOnOff.class,
				ArpeggiatorOnOff.class, SequenceurOnOff.class,
				AssignableParameter1.class, AssignableParameter2.class,
				AssignableParameter3.class, AssignableParameter4.class,
				FeedbackGain.class, PushItNote.class, PushItVelocity.class,
				PushItMode.class, SubOsc1Level.class, SubOsc2Level.class,
				FeedbackVolume.class, EditorByte.class, SplitPoint.class,
				KeyboardMode.class };

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
		result.add(layers[i].clockBPM);
		result.add(layers[i].pitchWheelRange);
		result.add(layers[i].sequencerTrigger);
		result.add(layers[i].keyMode);
		result.add(layers[i].unissonMode);
		result.add(layers[i].arpeggiatorMode);
		result.add(layers[i].envelope3Repeat);
		result.add(layers[i].unissonOnOff);
		result.add(layers[i].arpeggiatorOnOff);
		result.add(layers[i].sequencerOnOff);
		result.add(layers[i].assignableParameter1);
		result.add(layers[i].assignableParameter2);
		result.add(layers[i].assignableParameter3);
		result.add(layers[i].assignableParameter4);
		result.add(layers[i].feedbackGain);
		result.add(layers[i].pushItNote);
		result.add(layers[i].pushItVelocity);
		result.add(layers[i].pushItMode);
		result.add(layers[i].subOsc1Level);
		result.add(layers[i].subOsc2Level);
		result.add(layers[i].feedbackVolume);
		result.add(layers[i].editorByte);
		result.add(layers[i].splitPoint);
		result.add(layers[i].keyboardMode);

	}

	public Layer currentLayer() {
		return CurrentLayer;
	}

}
