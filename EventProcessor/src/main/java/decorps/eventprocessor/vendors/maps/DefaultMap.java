package decorps.eventprocessor.vendors.maps;

import java.lang.reflect.InvocationTargetException;

import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.messages.EventProcessorMidiMessage;
import decorps.eventprocessor.messages.EventProcessorShortMessage;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;
import decorps.eventprocessor.vendors.dsi.programparameters.ProgramParameter;
import decorps.eventprocessor.vendors.dsi.programparameters.ZeroTo127Range;
import decorps.eventprocessor.vendors.livid.BankLayout;

public class DefaultMap {

	public final Class<? extends ProgramParameter> abstractProgramParameterClass;
	public final byte bank;
	public final byte controllerNumber;

	public DefaultMap(
			Class<? extends ProgramParameter> abstractProgramParameterClass,
			int bank, int controllerNumber) {
		this.abstractProgramParameterClass = abstractProgramParameterClass;
		this.bank = (byte) bank;
		this.controllerNumber = (byte) controllerNumber;
	}

	public DefaultMap(
			Class<? extends ProgramParameter> abstractProgramParameterClass,
			int controllerNumber) {
		this(abstractProgramParameterClass, BankLayout.CurrentBank.bankNumber,
				controllerNumber);
	}

	public EventProcessorMidiMessage mapFromTetraToLivid(
			EventProcessorMidiMessage messageIn) {
		throw new EventProcessorException("Not Implemented");
	}

	public EventProcessorMidiMessage mapFromLividToTetra(
			EventProcessorMidiMessage messageIn) {
		EventProcessorShortMessage shortMessage = messageIn.getAsShortMessage();
		ProgramParameter programParameter = buildParameter(shortMessage);
		return DsiMessageFactory.buildNRPNForProgramParameter(programParameter);
	}

	public ProgramParameter buildParameter(
			EventProcessorShortMessage shortMessage) {
		if (!ZeroTo127Range.class
				.isAssignableFrom(abstractProgramParameterClass))
			throw new EventProcessorException("Not Supported");
		try {
			return abstractProgramParameterClass.getDeclaredConstructor(
					int.class, byte.class).newInstance(13,
					(byte) shortMessage.getData2());
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