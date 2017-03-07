package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.Authentication;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.ExceptionInfo;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.MandatoryEmptyFieldException;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.NullRequestException;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.WrapperConfigException;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFDocumentType;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFResponse;
import es.meyss.sgtic.sige.portafirmaswrapper.wrapper.IPortafirmasWrapper;
import es.meyss.sgtic.sige.portafirmaswrapper.wrapper.impl.PortaFirmasWrapperImpl;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestClient {

	private Authentication authentication;
	
	private String application;
	
	private IPortafirmasWrapper portafirmasWrapper;
	
	private static String requestId;
	
	@SuppressWarnings("unused")
	private static PFResponse response;
	
	@Before
	public void setUp() throws WrapperConfigException, ServiceException {
		authentication = new Authentication("GEPRECO", "a38babd5b1c71f2d2ae011a6e07a8ac3341d773d93a348363be8e6b95f492b18");
		application = "GEPRECO";
		portafirmasWrapper = new PortaFirmasWrapperImpl(authentication, application);
	}
	
	@Test(expected=WrapperConfigException.class)
	public void test1Config() throws WrapperConfigException, ServiceException{
		portafirmasWrapper = new PortaFirmasWrapperImpl(null, null);
	}
	
	@Test(expected=MandatoryEmptyFieldException.class)
	public void test2Mandatory() throws WrapperConfigException, ServiceException, ExceptionInfo, RemoteException, MandatoryEmptyFieldException, NullRequestException {
		portafirmasWrapper.createRequest();
		portafirmasWrapper.sendRequest();
	}
	
	@Test
	public void test3DefaultRequestCompleted() throws Exception {
		portafirmasWrapper.createRequest();
		portafirmasWrapper.addDocument(new byte[100], "application/pdf", "nombre_wrapper", PFDocumentType.GENERICO);
		portafirmasWrapper.addSignLine(0);
		portafirmasWrapper.addSigner(0, "48970544W");
		requestId = portafirmasWrapper.sendRequest();
		
		assertFalse(requestId == null || requestId.isEmpty());
	}
	
	@Test
	public void test4GetRequest() {
		try {
			response = portafirmasWrapper.getRequest(requestId);
		} catch (ExceptionInfo e) {
			fail("ExceptionInfo caught");
		} catch (RemoteException e) {
			fail("RemoteException caught");
		}
	}
	
	@Test
	public void test5DropRequest() {
		try {
			portafirmasWrapper.deleteRequest(requestId);
		} catch (ExceptionInfo e) {
			fail("ExceptionInfo caught");
		} catch (RemoteException e) {
			fail("RemoteException caught");
		}
	}

	
	@Test(expected=NullRequestException.class)
	public void test6SendEmptyRequest() throws Exception {
		portafirmasWrapper.sendRequest();
	}
}
