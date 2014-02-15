package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class Layer {
	public final ProgramParameter oscillator1Frequency;
	public final ProgramParameter oscillator1FineTune;
	public final ProgramParameter oscillator1Shape;
	public final ProgramParameter oscillator1Glide;
	public final ProgramParameter oscillator1Keyboard;
	public final ProgramParameter oscillator2Frequency;
	public final ProgramParameter oscillator2FineTune;
	public final ProgramParameter oscillator2Shape;
	public final ProgramParameter oscillator2Glide;
	public final ProgramParameter oscillator2Keyboard;
	public final ProgramParameter oscillatorHardSync;
	public final ProgramParameter glideMode;
	public final ProgramParameter oscillatorSlop;
	public final ProgramParameter oscillatorMix;
	public final ProgramParameter noiseLevel;
	public final ProgramParameter filterCutoffFreq;
	public final ProgramParameter filterResonance;
	public final ProgramParameter filterKeyboardAmt;
	public final ProgramParameter filterAudioMod;
	public final ProgramParameter filterConfigMode;
	public final ProgramParameter filterEnvAmount;
	public final ProgramParameter filterEnvVelocity;
	public final ProgramParameter filterEnvDelay;
	public final ProgramParameter filterEnvAttack;
	public final ProgramParameter filterEnvDecay;
	public final ProgramParameter filterEnvSustain;
	public final ProgramParameter filterEnvRelease;
	public final ProgramParameter vcaLevel;
	public final ProgramParameter panSpread;
	public final ProgramParameter programVolume;
	public final ProgramParameter vcaEnvAmount;
	public final ProgramParameter vcaEnvVelocity;
	public final ProgramParameter vcaEnvDelay;
	public final ProgramParameter vcaEnvAttack;
	public final ProgramParameter vcaEnvDecay;
	public final ProgramParameter vcaEnvSustain;
	public final ProgramParameter vcaEnvRelease;
	public final ProgramParameter lfo1frequency;
	public final ProgramParameter lfo1Shape;
	public final ProgramParameter lfo1Amount;
	public final ProgramParameter lfo1Destination;
	public final ProgramParameter lfo1KeySync;
	public final ProgramParameter lfo2frequency;
	public final ProgramParameter lfo2Shape;
	public final ProgramParameter lfo2Amount;
	public final ProgramParameter lfo2Destination;
	public final ProgramParameter lfo2KeySync;
	public final ProgramParameter lfo3frequency;
	public final ProgramParameter lfo3Shape;
	public final ProgramParameter lfo3Amount;
	public final ProgramParameter lfo3Destination;
	public final ProgramParameter lfo3KeySync;
	public final ProgramParameter lfo4frequency;
	public final ProgramParameter lfo4Shape;
	public final ProgramParameter lfo4Amount;
	public final ProgramParameter lfo4Destination;
	public final ProgramParameter lfo4KeySync;
	public final ProgramParameter env3Destination;
	public final ProgramParameter envelope3Amount;
	public final ProgramParameter envelope3Velocity;
	public final ProgramParameter envelope3Delay;
	public final ProgramParameter envelope3Attack;
	public final ProgramParameter envelope3Decay;
	public final ProgramParameter envelope3Sustain;
	public final ProgramParameter envelope3Release;
	public final ProgramParameter mod1Source;
	public final ProgramParameter mod1Amount;
	public final ProgramParameter mod1Destination;
	public final ProgramParameter mod2Source;
	public final ProgramParameter mod2Amount;
	public final ProgramParameter mod2Destination;
	public final ProgramParameter mod3Source;
	public final ProgramParameter mod3Amount;
	public final ProgramParameter mod3Destination;
	public final ProgramParameter mod4Source;
	public final ProgramParameter mod4Amount;
	public final ProgramParameter mod4Destination;
	public final ProgramParameter seq1Destination;
	public final ProgramParameter seq2Destination;
	public final ProgramParameter seq3Destination;
	public final ProgramParameter seq4Destination;
	public final ProgramParameter modWheelAmount;
	public final ProgramParameter modWheelDest;
	public final ProgramParameter pressureAmount;
	public final ProgramParameter pressureDestination;
	public final ProgramParameter breathAmount;
	public final ProgramParameter breathDestination;
	public final ProgramParameter velocityAmount;
	public final ProgramParameter velocityDestination;
	public final ProgramParameter footControlAmt;
	public final ProgramParameter footControlDest;
	public final ProgramParameter clockBPM;
	public final ProgramParameter clockDivide;
	public final ProgramParameter pitchWheelRange;
	public final ProgramParameter sequencerTrigger;
	public final ProgramParameter keyMode;
	public final ProgramParameter unissonMode;
	public final ProgramParameter arpeggiatorMode;
	public final ProgramParameter envelope3Repeat;
	public final ProgramParameter unissonOnOff;
	public final ProgramParameter arpeggiatorOnOff;
	public final ProgramParameter sequencerOnOff;
	public final ProgramParameter assignableParameter1;
	public final ProgramParameter assignableParameter2;
	public final ProgramParameter assignableParameter3;
	public final ProgramParameter assignableParameter4;
	public final ProgramParameter feedbackGain;
	public final ProgramParameter pushItNote;
	public final ProgramParameter pushItVelocity;
	public final ProgramParameter pushItMode;
	public final ProgramParameter subOsc1Level;
	public final ProgramParameter subOsc2Level;
	public final ProgramParameter feedbackVolume;
	public final ProgramParameter editorByte;
	public final ProgramParameter splitPoint;
	public final ProgramParameter keyboardMode;

	Layer(Class<? extends ProgramParameter>[] parametersClasses, byte[] data,
			int offset) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		ParameterFactory factory = new ParameterFactory(data, offset,
				parametersClasses);
		oscillator1Frequency = factory.buildNextParameter();
		oscillator1FineTune = factory.buildNextParameter();
		oscillator1Shape = factory.buildNextParameter();
		oscillator1Glide = factory.buildNextParameter();
		oscillator1Keyboard = factory.buildNextParameter();
		oscillator2Frequency = factory.buildNextParameter();
		oscillator2FineTune = factory.buildNextParameter();
		oscillator2Shape = factory.buildNextParameter();
		oscillator2Glide = factory.buildNextParameter();
		oscillator2Keyboard = factory.buildNextParameter();
		oscillatorHardSync = factory.buildNextParameter();
		glideMode = factory.buildNextParameter();
		oscillatorSlop = factory.buildNextParameter();
		oscillatorMix = factory.buildNextParameter();
		noiseLevel = factory.buildNextParameter();
		filterCutoffFreq = factory.buildNextParameter();
		filterResonance = factory.buildNextParameter();
		filterKeyboardAmt = factory.buildNextParameter();
		filterAudioMod = factory.buildNextParameter();
		filterConfigMode = factory.buildNextParameter();
		filterEnvAmount = factory.buildNextParameter();
		filterEnvVelocity = factory.buildNextParameter();
		filterEnvDelay = factory.buildNextParameter();
		filterEnvAttack = factory.buildNextParameter();
		filterEnvDecay = factory.buildNextParameter();
		filterEnvSustain = factory.buildNextParameter();
		filterEnvRelease = factory.buildNextParameter();
		vcaLevel = factory.buildNextParameter();
		panSpread = factory.buildNextParameter();
		programVolume = factory.buildNextParameter();
		vcaEnvAmount = factory.buildNextParameter();
		vcaEnvVelocity = factory.buildNextParameter();
		vcaEnvDelay = factory.buildNextParameter();
		vcaEnvAttack = factory.buildNextParameter();
		vcaEnvDecay = factory.buildNextParameter();
		vcaEnvSustain = factory.buildNextParameter();
		vcaEnvRelease = factory.buildNextParameter();
		lfo1frequency = factory.buildNextParameter();
		lfo1Shape = factory.buildNextParameter();
		lfo1Amount = factory.buildNextParameter();
		lfo1Destination = factory.buildNextParameter();
		lfo1KeySync = factory.buildNextParameter();
		lfo2frequency = factory.buildNextParameter();
		lfo2Shape = factory.buildNextParameter();
		lfo2Amount = factory.buildNextParameter();
		lfo2Destination = factory.buildNextParameter();
		lfo2KeySync = factory.buildNextParameter();
		lfo3frequency = factory.buildNextParameter();
		lfo3Shape = factory.buildNextParameter();
		lfo3Amount = factory.buildNextParameter();
		lfo3Destination = factory.buildNextParameter();
		lfo3KeySync = factory.buildNextParameter();
		lfo4frequency = factory.buildNextParameter();
		lfo4Shape = factory.buildNextParameter();
		lfo4Amount = factory.buildNextParameter();
		lfo4Destination = factory.buildNextParameter();
		lfo4KeySync = factory.buildNextParameter();
		env3Destination = factory.buildNextParameter();
		envelope3Amount = factory.buildNextParameter();
		envelope3Velocity = factory.buildNextParameter();
		envelope3Delay = factory.buildNextParameter();
		envelope3Attack = factory.buildNextParameter();
		envelope3Decay = factory.buildNextParameter();
		envelope3Sustain = factory.buildNextParameter();
		envelope3Release = factory.buildNextParameter();
		mod1Source = factory.buildNextParameter();
		mod1Amount = factory.buildNextParameter();
		mod1Destination = factory.buildNextParameter();
		mod2Source = factory.buildNextParameter();
		mod2Amount = factory.buildNextParameter();
		mod2Destination = factory.buildNextParameter();
		mod3Source = factory.buildNextParameter();
		mod3Amount = factory.buildNextParameter();
		mod3Destination = factory.buildNextParameter();
		mod4Source = factory.buildNextParameter();
		mod4Amount = factory.buildNextParameter();
		mod4Destination = factory.buildNextParameter();
		seq1Destination = factory.buildNextParameter();
		seq2Destination = factory.buildNextParameter();
		seq3Destination = factory.buildNextParameter();
		seq4Destination = factory.buildNextParameter();
		modWheelAmount = factory.buildNextParameter();
		modWheelDest = factory.buildNextParameter();
		pressureAmount = factory.buildNextParameter();
		pressureDestination = factory.buildNextParameter();
		breathAmount = factory.buildNextParameter();
		breathDestination = factory.buildNextParameter();
		velocityAmount = factory.buildNextParameter();
		velocityDestination = factory.buildNextParameter();
		footControlAmt = factory.buildNextParameter();
		footControlDest = factory.buildNextParameter();
		clockBPM = factory.buildNextParameter();
		clockDivide = factory.buildNextParameter();
		pitchWheelRange = factory.buildNextParameter();
		sequencerTrigger = factory.buildNextParameter();
		keyMode = factory.buildNextParameter();
		unissonMode = factory.buildNextParameter();
		arpeggiatorMode = factory.buildNextParameter();
		envelope3Repeat = factory.buildNextParameter();
		unissonOnOff = factory.buildNextParameter();
		arpeggiatorOnOff = factory.buildNextParameter();
		sequencerOnOff = factory.buildNextParameter();
		assignableParameter1 = factory.buildNextParameter();
		assignableParameter2 = factory.buildNextParameter();
		assignableParameter3 = factory.buildNextParameter();
		assignableParameter4 = factory.buildNextParameter();
		feedbackGain = factory.buildNextParameter();
		pushItNote = factory.buildNextParameter();
		pushItVelocity = factory.buildNextParameter();
		pushItMode = factory.buildNextParameter();
		subOsc1Level = factory.buildNextParameter();
		subOsc2Level = factory.buildNextParameter();
		feedbackVolume = factory.buildNextParameter();
		editorByte = factory.buildNextParameter();
		splitPoint = factory.buildNextParameter();
		keyboardMode = factory.buildNextParameter();
	}

	@Override
	public String toString() {
		return "Layer [oscillator1Frequency=" + oscillator1Frequency
				+ ", oscillator1FineTune=" + oscillator1FineTune
				+ ", oscillator1Shape=" + oscillator1Shape
				+ ", oscillator1Glide=" + oscillator1Glide
				+ ", oscillator1Keyboard=" + oscillator1Keyboard
				+ ", oscillator2Frequency=" + oscillator2Frequency
				+ ", oscillator2FineTune=" + oscillator2FineTune
				+ ", oscillator2Shape=" + oscillator2Shape
				+ ", oscillator2Glide=" + oscillator2Glide
				+ ", oscillator2Keyboard=" + oscillator2Keyboard
				+ ", oscillatorHardSync=" + oscillatorHardSync + ", glideMode="
				+ glideMode + ", oscillatorSlop=" + oscillatorSlop
				+ ", oscillatorMix=" + oscillatorMix + ", noiseLevel="
				+ noiseLevel + ", filterCutoffFreq=" + filterCutoffFreq
				+ ", filterResonance=" + filterResonance
				+ ", filterKeyboardAmt=" + filterKeyboardAmt
				+ ", filterAudioMod=" + filterAudioMod + ", filterConfigMode="
				+ filterConfigMode + ", filterEnvAmount=" + filterEnvAmount
				+ ", filterEnvVelocity=" + filterEnvVelocity
				+ ", filterEnvDelay=" + filterEnvDelay + ", filterEnvAttack="
				+ filterEnvAttack + ", filterEnvDecay=" + filterEnvDecay
				+ ", filterEnvSustain=" + filterEnvSustain
				+ ", filterEnvRelease=" + filterEnvRelease + ", vcaLevel="
				+ vcaLevel + ", panSpread=" + panSpread + ", programVolume="
				+ programVolume + ", vcaEnvAmount=" + vcaEnvAmount
				+ ", vcaEnvVelocity=" + vcaEnvVelocity + ", vcaEnvDelay="
				+ vcaEnvDelay + ", vcaEnvAttack=" + vcaEnvAttack
				+ ", vcaEnvDecay=" + vcaEnvDecay + ", vcaEnvSustain="
				+ vcaEnvSustain + ", vcaEnvRelease=" + vcaEnvRelease
				+ ", lfo1frequency=" + lfo1frequency + ", lfo1Shape="
				+ lfo1Shape + ", lfo1Amount=" + lfo1Amount
				+ ", lfo1Destination=" + lfo1Destination + ", lfo1KeySync="
				+ lfo1KeySync + ", lfo2frequency=" + lfo2frequency
				+ ", lfo2Shape=" + lfo2Shape + ", lfo2Amount=" + lfo2Amount
				+ ", lfo2Destination=" + lfo2Destination + ", lfo2KeySync="
				+ lfo2KeySync + ", lfo3frequency=" + lfo3frequency
				+ ", lfo3Shape=" + lfo3Shape + ", lfo3Amount=" + lfo3Amount
				+ ", lfo3Destination=" + lfo3Destination + ", lfo3KeySync="
				+ lfo3KeySync + ", lfo4frequency=" + lfo4frequency
				+ ", lfo4Shape=" + lfo4Shape + ", lfo4Amount=" + lfo4Amount
				+ ", lfo4Destination=" + lfo4Destination + ", lfo4KeySync="
				+ lfo4KeySync + ", env3Destination=" + env3Destination
				+ ", envelope3Amount=" + envelope3Amount
				+ ", envelope3Velocity=" + envelope3Velocity
				+ ", envelope3Delay=" + envelope3Delay + ", envelope3Attack="
				+ envelope3Attack + ", envelope3Decay=" + envelope3Decay
				+ ", envelope3Sustain=" + envelope3Sustain
				+ ", envelope3Release=" + envelope3Release + ", mod1Source="
				+ mod1Source + ", mod1Amount=" + mod1Amount
				+ ", mod1Destination=" + mod1Destination + ", mod2Source="
				+ mod2Source + ", mod2Amount=" + mod2Amount
				+ ", mod2Destination=" + mod2Destination + ", mod3Source="
				+ mod3Source + ", mod3Amount=" + mod3Amount
				+ ", mod3Destination=" + mod3Destination + ", mod4Source="
				+ mod4Source + ", mod4Amount=" + mod4Amount
				+ ", mod4Destination=" + mod4Destination + ", seq1Destination="
				+ seq1Destination + ", seq2Destination=" + seq2Destination
				+ ", seq3Destination=" + seq3Destination + ", seq4Destination="
				+ seq4Destination + ", modWheelAmount=" + modWheelAmount
				+ ", modWheelDest=" + modWheelDest + ", pressureAmount="
				+ pressureAmount + ", pressureDestination="
				+ pressureDestination + ", breathAmount=" + breathAmount
				+ ", breathDestination=" + breathDestination
				+ ", velocityAmount=" + velocityAmount
				+ ", velocityDestination=" + velocityDestination
				+ ", footControlAmt=" + footControlAmt + ", footControlDest="
				+ footControlDest + "]";
	}

	public static Layer build(
			Class<? extends ProgramParameter>[] parametersClasses, byte[] data,
			int offset) {
		try {
			return new Layer(parametersClasses, data, offset);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new EventProcessorException(e);
		}
	}

	public List<ProgramParameter> getProgramParameters() {
		return ParameterFactory.layerAParameters;
	}

	public byte getValueFor(ProgramParameter programParameter) {
		for (ProgramParameter programParameterCandidate : ParameterFactory.layerAParameters) {
			if (programParameterCandidate.getClass().equals(
					programParameter.getClass()))
				return programParameterCandidate.getValue();
		}
		throw new EventProcessorException(
				"Could not find value for parameter: " + programParameter);
	}

}
