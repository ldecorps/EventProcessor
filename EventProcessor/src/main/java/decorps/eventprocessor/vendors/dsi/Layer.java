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
				+ lfo4KeySync + "]";
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
