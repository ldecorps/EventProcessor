package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;

import decorps.eventprocessor.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.Oscillator1Frequency;

public class Layer {
	public final Oscillator1Frequency oscillator1Frequency;

	public Layer(Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte b) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		oscillator1Frequency = (Oscillator1Frequency) parametersClasses[0]
				.getConstructor(byte.class).newInstance(b);
	}

	@Override
	public String toString() {
		return "Layer [oscillator1Frequency=" + oscillator1Frequency + "]";
	}

	public static Layer build(
			Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte b) {
		try {
			return new Layer(parametersClasses, b);
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
