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
				+ ", oscillator2Keyboard=" + oscillator2Keyboard + "]";
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
