package decorps.eventprocessor.vendors.dsi;

import java.util.Arrays;

import decorps.eventprocessor.messages.EventProcessorSysexMessage;

public class ProgramDataDump extends EventProcessorSysexMessage {
	@Override
	public String toString() {
		return "ProgramDataDump [bankNumber=" + bankNumber + ", programNumber="
				+ programNumber + "]";
	}

	public final byte[] sysexMessage;
	public final byte bankNumber;
	public final byte programNumber;
	public final ProgramParameterData programParameterData;
	public final byte[] unpacked;

	ProgramDataDump(byte[] sysexMessage) {
		super(sysexMessage);
		this.sysexMessage = sysexMessage;
		bankNumber = sysexMessage[4];
		programNumber = sysexMessage[5];
		unpacked = unpack(Arrays.copyOfRange(sysexMessage, 6,
				sysexMessage.length + 1));
		programParameterData = ProgramParameterData.build(unpacked);
	}

	public static ProgramDataDump buildProgramDataDump(byte[] sysexMessage) {
		ProgramDataDump programDataDump = new ProgramDataDump(sysexMessage);
		return programDataDump;
	}

}
