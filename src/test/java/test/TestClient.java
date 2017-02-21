package test;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.junit.Test;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.Authentication;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.ExceptionInfo;
import es.meyss.sgtic.sige.portafirmaswrapper.constant.PFDocumentType;
import es.meyss.sgtic.sige.portafirmaswrapper.constant.PFImportanceLevel;
import es.meyss.sgtic.sige.portafirmaswrapper.constant.PFSignType;
import es.meyss.sgtic.sige.portafirmaswrapper.wrapper.IPortafirmasWrapper;
import es.meyss.sgtic.sige.portafirmaswrapper.wrapper.impl.PortaFirmasWrapperImpl;

public class TestClient {

	@Test
	public void test() {
		try {
			/*Authentication authentication = new Authentication("GEPRECO",
					"a38babd5b1c71f2d2ae011a6e07a8ac3341d773d93a348363be8e6b95f492b18");
			String application = "GEPRECO";

			IPortafirmasWrapper portafirmas = new PortaFirmasWrapperImpl(authentication, application);
			String idRequest = portafirmas.createRequest("test", PFImportanceLevel.NORMAL, PFSignType.CASCADA);
			String idDocument = portafirmas.addDocument(new byte[0], "application/pdf", "test byte",
					PFDocumentType.GENERICO);
			portafirmas.sendRequest();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
