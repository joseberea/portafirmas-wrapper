package es.meyss.sgtic.sige.portafirmaswrapper.wrapper;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import es.meyss.sgtic.sige.portafirmas.client.ws.admin.AdminService_PortType;
import es.meyss.sgtic.sige.portafirmas.client.ws.admin.AdminService_ServiceLocator;
import es.meyss.sgtic.sige.portafirmas.client.ws.modify.ModifyService_PortType;
import es.meyss.sgtic.sige.portafirmas.client.ws.modify.ModifyService_ServiceLocator;
import es.meyss.sgtic.sige.portafirmas.client.ws.query.QueryService_PortType;
import es.meyss.sgtic.sige.portafirmas.client.ws.query.QueryService_ServiceLocator;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.Authentication;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.Document;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.ExceptionInfo;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.Request;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.SignLine;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.SignType;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.User;

@SuppressWarnings("unused")
public class PortafirmasWrapper {

	private final QueryService_PortType wsQueryService;

	private final ModifyService_PortType wsModifyService;

	private final AdminService_PortType wsAdminService;

	private final Authentication authentication;

	private Request request;

	private Calendar calendar = new GregorianCalendar();

	/**
	 * 
	 * @param authentication
	 * @throws ServiceException
	 */
	public PortafirmasWrapper(Authentication authentication) throws ServiceException {
		ModifyService_ServiceLocator serviceLocatorModify = new ModifyService_ServiceLocator();
		wsModifyService = serviceLocatorModify.getModifyServicePort();
		QueryService_ServiceLocator serviceLocatorQuery = new QueryService_ServiceLocator();
		wsQueryService = serviceLocatorQuery.getQueryServicePort();
		AdminService_ServiceLocator serviceLocatorAdmin = new AdminService_ServiceLocator();
		wsAdminService = serviceLocatorAdmin.getAdminServicePort();
		this.authentication = authentication;
	}

	/**
	 * 
	 * @param document
	 * @param application
	 * @param importanceLevel
	 * @param signLineList
	 * @return
	 * @throws ExceptionInfo
	 * @throws RemoteException
	 */
	public Request sendDocument(Document document, String application, es.meyss.sgtic.sige.portafirmas.client.ws.type.ImportanceLevel importanceLevel,
			SignLine[] signLineList) throws ExceptionInfo, RemoteException {
		String requestId, documentId;
		initializeRequest(document.getName(), application, importanceLevel, signLineList);
		requestId = wsModifyService.createRequest(authentication, request);
		request.setIdentifier(requestId);
		documentId = wsModifyService.insertDocument(authentication, requestId, document);
		document.setIdentifier(documentId);
		request.setDocumentList(new Document[] { document });
		wsModifyService.sendRequest(authentication, new StringHolder(requestId));
		return request;
	}

	/**
	 * 
	 * @param documentId
	 * @throws ExceptionInfo
	 * @throws RemoteException
	 */
	public void deleteDocument(final String documentId) throws ExceptionInfo, RemoteException {
		wsModifyService.deleteDocument(authentication, new StringHolder(documentId));
	}

	/**
	 * 
	 * @param requestId
	 * @throws ExceptionInfo
	 * @throws RemoteException
	 */
	public void deleteRequest(final String requestId) throws ExceptionInfo, RemoteException {
		wsModifyService.removeRequest(authentication, new StringHolder(requestId), " -- Nothing -- ");
	}

	/**
	 * 
	 * @param requestId
	 * @param reason
	 * @throws ExceptionInfo
	 * @throws RemoteException
	 */
	public void deleteRequest(final String requestId, final String reason) throws ExceptionInfo, RemoteException {
		wsModifyService.removeRequest(authentication, new StringHolder(requestId), reason);
	}

	/**
	 * 
	 * @param subject
	 * @param application
	 * @param importanceLevel
	 * @param signLineList
	 */
	private void initializeRequest(String subject, String application, es.meyss.sgtic.sige.portafirmas.client.ws.type.ImportanceLevel importanceLevel,
			SignLine[] signLineList) {
		request = new Request();
		request.setSubject(subject);
		request.setFentry(calendar);
		request.setFstart(calendar);
		request.setSignType(SignType.value2);
		request.setApplication(application);
		request.setImportanceLevel(importanceLevel);
		request.setSignLineList(signLineList);
		request.setRemitterList(new User[0]);
	}
}
