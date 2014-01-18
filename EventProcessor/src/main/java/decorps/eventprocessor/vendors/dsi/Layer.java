package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class Layer {
	public final AbstractProgramParameter oscillator1Frequency;
	public final AbstractProgramParameter oscillator1FineTune;
	public final AbstractProgramParameter oscillator1Shape;
	public final AbstractProgramParameter oscillator1Glide;
	private final Class<? extends AbstractProgramParameter>[] parametersClasses;
	private final byte[] data;
	private final int offset;
	int parameterIndex = 0;

	Layer(Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte[] data, int offset) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		this.parametersClasses = parametersClasses;
		this.data = data;
		this.offset = offset;
		oscillator1Frequency = buildNextParameter();
		oscillator1FineTune = buildNextParameter();
		oscillator1Shape = buildNextParameter();
		oscillator1Glide = buildNextParameter();
	}

	private AbstractProgramParameter buildNextParameter()
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return instanciateParameter(parametersClasses, data, offset,
				parameterIndex++);
	}

	public AbstractProgramParameter instanciateParameter(
			Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte[] data, int offset, int parameterIndex)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		try {
			return parametersClasses[parameterIndex].getConstructor(byte.class)
					.newInstance(data[offset + parameterIndex]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new EventProcessorException("missing parameter class", e);
		}
	}

	@Override
	public String toString() {
		return "Layer [oscillator1Frequency=" + oscillator1Frequency
				+ ", oscillator1FineTune=" + oscillator1FineTune
				+ ", oscillator1Shape=" + oscillator1Shape + "]";
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
