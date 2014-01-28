package decorps.eventprocessor.vendors.dsi;

import java.util.Arrays;

import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class ProgramEditBufferDataDump extends EventProcessorSysexMessage {

	public final byte[] sysexMessage;
	public final ProgramParameterData programParameterData;
	public final byte[] unpacked;

	public ProgramEditBufferDataDump(byte[] sysexMessage) {
		super(sysexMessage);
		this.sysexMessage = sysexMessage;
		byte[] editBufferDataDump = Arrays.copyOfRange(sysexMessage, 4,
				sysexMessage.length);
		unpacked = unpack(editBufferDataDump);
		programParameterData = ProgramParameterData.build(unpacked);
	}

	public static ProgramEditBufferDataDump buildProgramEditBufferDataDump(
			byte[] sysexMessage) {
		ProgramEditBufferDataDump programDump = new ProgramEditBufferDataDump(
				sysexMessage);
		return programDump;
	}

	@Override
	public String toString() {
		return "ProgramEditBufferDataDump [programParameterData="
				+ programParameterData + "]";
	}

}
