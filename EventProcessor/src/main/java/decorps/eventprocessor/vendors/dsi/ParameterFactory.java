package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.AbstractProgramParameter;

public class ParameterFactory {
	private final Class<? extends AbstractProgramParameter>[] parametersClasses;
	private final byte[] data;
	private final int offset;
	int parameterIndex = 0;

	public AbstractProgramParameter buildNextParameter()
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return instanciateParameter(parametersClasses, data, offset,
				parameterIndex++);
	}

	public ParameterFactory(byte[] data, int offset,
			Class<? extends AbstractProgramParameter>[] parametersClasses) {
		super();
		this.parametersClasses = parametersClasses;
		this.data = data;
		this.offset = offset;
	}

	private AbstractProgramParameter instanciateParameter(
			Class<? extends AbstractProgramParameter>[] parametersClasses,
			byte[] data, int offset, int parameterIndex)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		try {
			return parametersClasses[parameterIndex].getConstructor(int.class,
					byte.class).newInstance(parameterIndex,
					data[offset + parameterIndex]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new EventProcessorException("missing parameter class", e);
		}
	}
}
