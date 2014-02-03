package decorps.eventprocessor.vendors.dsi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;

public class ParameterFactory {
	private final Class<? extends ProgramParameter>[] parametersClasses;
	private final byte[] data;
	private final int offset;
	int parameterIndex = 0;
	static List<ProgramParameter> layerAParameters = new ArrayList<ProgramParameter>();

	public ProgramParameter buildNextParameter() throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final ProgramParameter programParameter = instanciateParameter(
				parametersClasses, data, offset, parameterIndex++);
		layerAParameters.add(programParameter);
		return programParameter;
	}

	public ParameterFactory(byte[] data, int offset,
			Class<? extends ProgramParameter>[] parametersClasses) {
		super();
		this.parametersClasses = parametersClasses;
		this.data = data;
		this.offset = offset;
	}

	private ProgramParameter instanciateParameter(
			Class<? extends ProgramParameter>[] parametersClasses, byte[] data,
			int offset, int parameterIndex) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		try {
			System.out.println(parametersClasses[parameterIndex]
					.getSimpleName()
					+ " "
					+ parameterIndex
					+ " "
					+ data[offset + parameterIndex]);
			return parametersClasses[parameterIndex].getConstructor(int.class,
					byte.class).newInstance(parameterIndex,
					data[offset + parameterIndex]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new EventProcessorException("missing parameter class", e);
		}
	}
}
