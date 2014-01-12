package decorps.eventprocessor.vendors.dsi;

import java.util.Arrays;

import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class ProgramDump extends EventProcessorSysexMessage {
	@Override
	public String toString() {
		return "ProgramDump [bankNumber=" + bankNumber + ", programNumber="
				+ programNumber + "]";
	}

	public final byte[] sysexMessage;
	public final byte bankNumber;
	public final byte programNumber;
	public final byte[] programParameterData;

	public ProgramDump(byte[] sysexMessage) {
		super(sysexMessage);
		this.sysexMessage = sysexMessage;
		bankNumber = sysexMessage[4];
		programNumber = sysexMessage[5];
		programParameterData = unpack(Arrays.copyOfRange(sysexMessage, 6,
				sysexMessage.length - 1));
	}

	public static ProgramDump buildProgramDump(byte[] sysexMessage) {
		ProgramDump programDump = new ProgramDump(sysexMessage);
		return programDump;
	}
}
