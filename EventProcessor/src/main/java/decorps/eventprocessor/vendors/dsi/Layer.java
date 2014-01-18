package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class Layer {
	public final AbstractProgramParameter oscillator1Frequency;
	public final AbstractProgramParameter oscillator1FineTune;
	public final AbstractProgramParameter oscillator1Shape;
	public final AbstractProgramParameter oscillator1Glide;

	Layer(Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte[] data, int offset) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		ParameterFactory factory = new ParameterFactory(data, offset,
				parametersClasses);
		oscillator1Frequency = factory.buildNextParameter();
		oscillator1FineTune = factory.buildNextParameter();
		oscillator1Shape = factory.buildNextParameter();
		oscillator1Glide = factory.buildNextParameter();
	}

	@Override
	public String toString() {
		return "Layer [oscillator1Frequency=" + oscillator1Frequency
				+ ", oscillator1FineTune=" + oscillator1FineTune
				+ ", oscillator1Shape=" + oscillator1Shape
				+ ", oscillator1Glide=" + oscillator1Glide + "]";
	}

	public static Layer build(
			Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte[] data, int offset) {
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
}
