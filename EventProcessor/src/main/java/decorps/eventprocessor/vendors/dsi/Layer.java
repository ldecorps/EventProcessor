package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1FineTune;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Shape;

public class Layer {
	public final Oscillator1Frequency oscillator1Frequency;
	public final Oscillator1FineTune oscillator1FineTune;
	public final Oscillator1Shape oscillator1Shape;

	Layer(Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte[] data, int offset) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		oscillator1Frequency = (Oscillator1Frequency) parametersClasses[0]
				.getConstructor(byte.class).newInstance(data[offset]);
		oscillator1FineTune = (Oscillator1FineTune) parametersClasses[1]
				.getConstructor(byte.class).newInstance(data[offset + 1]);
		oscillator1Shape = (Oscillator1Shape) parametersClasses[2]
				.getConstructor(byte.class).newInstance(data[offset + 2]);
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
