package decorps.eventprocessor.snipets;

import static decorps.eventprocessor.utils.BaseUtils.bytesToHexa;
import decorps.eventprocessor.EventProcessor;
import decorps.eventprocessor.exceptions.EventProcessorException;
import decorps.eventprocessor.vendors.dsi.messages.DsiMessageFactory;

public class TestingDeviceInquiry {

	public static void main(String[] args) {
		EventProcessor eventProcessor = EventProcessor.build();

		eventProcessor.fromTetraToLivid.receiver.send(DsiMessageFactory
				.buildUniversal_System_Exclusive_Message_Device_Inquiry()
				.getAsSysexMessage(), -1);

		synchronized (eventProcessor.fromTetraToLivid.receiver.wait) {
			try {
				eventProcessor.fromTetraToLivid.receiver.wait.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EventProcessorException(e);
			}
		}
		System.out.println(bytesToHexa(eventProcessor.fromTetraToLivid.receiver
				.getSentMidiMessages().get(0).getMessage()));

	}

}
