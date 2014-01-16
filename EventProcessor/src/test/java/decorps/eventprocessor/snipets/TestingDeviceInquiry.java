package decorps.eventprocessor.snipets;

import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.RulesAwareReceiverWrapper;
import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.DsiTetraMap;

public class TestingDeviceInquiry {

	public static void main(String[] args) {
		EventProcessor eventProcessor = new EventProcessor();

		SysexMessage sysex;
		try {
			sysex = new SysexMessage();
			sysex.setMessage(
					DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry,
					DsiTetraMap.Universal_System_Exclusive_Message_Device_Inquiry.length);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
			throw new EventProcessorException(e1);
		}

		eventProcessor.fromTetraToLivid.receiver.send(sysex, -1);

		synchronized (RulesAwareReceiverWrapper.wait) {
			try {
				RulesAwareReceiverWrapper.wait.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		System.out.println(bytesToHexa(eventProcessor.fromTetraToLivid.receiver
				.getSentMidiMessages().get(0).getMessage()));

	}

}
