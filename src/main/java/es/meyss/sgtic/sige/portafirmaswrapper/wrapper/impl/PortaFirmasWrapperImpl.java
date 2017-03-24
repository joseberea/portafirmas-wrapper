package es.meyss.sgtic.sige.portafirmaswrapper.wrapper.impl;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.AxisProperties;
import org.apache.commons.io.IOUtils;

import es.meyss.sgtic.sige.portafirmas.client.ws.admin.AdminService_PortType;
import es.meyss.sgtic.sige.portafirmas.client.ws.admin.AdminService_ServiceLocator;
import es.meyss.sgtic.sige.portafirmas.client.ws.modify.ModifyService_PortType;
import es.meyss.sgtic.sige.portafirmas.client.ws.modify.ModifyService_ServiceLocator;
import es.meyss.sgtic.sige.portafirmas.client.ws.query.QueryService_PortType;
import es.meyss.sgtic.sige.portafirmas.client.ws.query.QueryService_ServiceLocator;
import es.meyss.sgtic.sige.portafirmas.type.Authentication;
import es.meyss.sgtic.sige.portafirmas.type.Document;
import es.meyss.sgtic.sige.portafirmas.type.DocumentType;
import es.meyss.sgtic.sige.portafirmas.type.ExceptionInfo;
import es.meyss.sgtic.sige.portafirmas.type.ImportanceLevel;
import es.meyss.sgtic.sige.portafirmas.type.Request;
import es.meyss.sgtic.sige.portafirmas.type.SignLine;
import es.meyss.sgtic.sige.portafirmas.type.SignLineType;
import es.meyss.sgtic.sige.portafirmas.type.SignType;
import es.meyss.sgtic.sige.portafirmas.type.Signer;
import es.meyss.sgtic.sige.portafirmas.type.User;
import es.meyss.sgtic.sige.portafirmaswrapper.XOPHandler;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.MandatoryEmptyFieldException;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.NullRequestException;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.WrapperConfigException;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFDocument;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFDocumentStatus;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFImportanceLevel;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFRequest;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFResponse;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFStatusType;
import es.meyss.sgtic.sige.portafirmaswrapper.wrapper.IPortafirmasWrapper;

public class PortaFirmasWrapperImpl implements IPortafirmasWrapper {

	private final Calendar calendar = new GregorianCalendar();

	private final QueryService_PortType wsQueryService;

	private final ModifyService_PortType wsModifyService;

	@SuppressWarnings("unused")
	private final AdminService_PortType wsAdminService;

	private final Authentication authentication;

	private final String application;

	private Request request;

	private Map<Integer, SignLine> signLineMap = new LinkedHashMap<Integer, SignLine>();
	private Map<Integer, LinkedList<Signer>> signerMap = new LinkedHashMap<Integer, LinkedList<Signer>>();
	private List<Document> documentList = new LinkedList<Document>();

	public PortaFirmasWrapperImpl(Authentication authentication, String application) throws WrapperConfigException, ServiceException {
		AxisProperties.setProperty("axis.ClientConfigFile", "src/main/java/es/meyss/sgtic/sige/portafirmaswrapper/client-config.wsdd");
		if(authentication == null) {
			throw new WrapperConfigException("authentication");
		}
		if(application == null) {
			throw new WrapperConfigException("application");
		}
		ModifyService_ServiceLocator serviceLocatorModify = new ModifyService_ServiceLocator();
		wsModifyService = serviceLocatorModify.getModifyServicePort();
		QueryService_ServiceLocator serviceLocatorQuery = new QueryService_ServiceLocator();
		wsQueryService = serviceLocatorQuery.getQueryServicePort();
		AdminService_ServiceLocator serviceLocatorAdmin = new AdminService_ServiceLocator();
		wsAdminService = serviceLocatorAdmin.getAdminServicePort();
		this.authentication = authentication;
		this.application = application;
	}

	@Override
	public void createRequest() {
		request = new Request();
		request.setSubject(" -- No subject -- ");
		request.setFentry(calendar);
		request.setFstart(calendar);
		request.setSignType(SignType.value2);
		request.setApplication(application);
		request.setImportanceLevel(PFImportanceLevel.NORMAL);
		request.setSignLineList(new SignLine[0]);
		request.setRemitterList(new User[0]);
	}

	@Override
	public void createRequest(final String subject, final ImportanceLevel importanceLevel, SignType signType) throws MandatoryEmptyFieldException {
		if(importanceLevel == null) {
			throw new MandatoryEmptyFieldException("ImportanceLevel");
		}
		if(signType == null) {
			throw new MandatoryEmptyFieldException("SignType");
		}
		request = new Request();
		request.setSubject(subject);
		request.setFentry(calendar);
		request.setFstart(calendar);
		request.setSignType(signType);
		request.setApplication(application);
		request.setImportanceLevel(importanceLevel);
		request.setSignLineList(new SignLine[0]);
		request.setRemitterList(new User[0]);
	}
	
	@Override
	public void setRequestSubject(final String subject) {
		request.setSubject(subject);
	}

	@Override
	public void setRequestImportanceLevel(final ImportanceLevel importanceLevel) {
		request.setImportanceLevel(importanceLevel);
	}

	@Override
	public void setRequestSignType(SignType signType) {
		request.setSignType(signType);
	}
	
	@Override
	public PFResponse getRequest(String requestId) throws ExceptionInfo, RemoteException {
		PFResponse response = new PFResponse();
		Request request = wsQueryService.queryRequest(authentication, requestId);
		response.setRequest(new PFRequest(request));
		for(Document document : request.getDocumentList()) {
			response.getDocuments().put(document.getIdentifier(), new PFDocument(document));
		}
		return response;
	}
	
	@Override
	public PFDocumentStatus getDocumentStatus(String documentId, String requestId) throws ExceptionInfo, RemoteException, IOException {
		PFDocumentStatus status = new PFDocumentStatus();
		
		Request request = wsQueryService.queryRequest(authentication, requestId);
		if(request.getRequestStatus().equals(PFStatusType.ACEPTADO)) {
			// Documento firmado, actualizamos el valor del content
			status.setContent(getSign(documentId));
		} else {
			status.setContent(getDocument(documentId));
		}
		status.setStatus(new PFStatusType(request.getRequestStatus().getValue()));
		return status;
	}
	
	@Override
	public void deleteRequest(final String requestId) throws ExceptionInfo, RemoteException {
		if(request != null && request.getIdentifier().equals(requestId)) {
			// Se elimina la request temporal
			clearRequest();
		} else {
			// Se elimina otra request
			wsModifyService.removeRequest(authentication, new StringHolder(requestId), "  -- Nothing -- ");
		}
	}

	@Override
	public String sendRequest() throws ExceptionInfo, RemoteException, MandatoryEmptyFieldException, NullRequestException {
		String requestId;
		Signer[] signerArray;
		SignLine[] signLineArray;
		Document[] documentArray;
		
		if(request == null) {
			throw new NullRequestException();
		}
		
		if(request.getImportanceLevel() == null) {
			throw new MandatoryEmptyFieldException("ImportanceLevel");
		}
		if(request.getSignType() == null) {
			throw new MandatoryEmptyFieldException("SignType");
		}
		
		if(documentList.size() == 0) {
			throw new MandatoryEmptyFieldException("Document");
		}
		
		if(signLineMap.values().size() == 0) {
			throw new MandatoryEmptyFieldException("SignLine");
		}
		
		// Singer
		for (Entry<Integer, LinkedList<Signer>> entry : signerMap.entrySet()) {
			if(entry.getValue().size() == 0) {
				throw new MandatoryEmptyFieldException("Signer", "SignLine", entry.getKey());
			}
			signerArray = new Signer[entry.getValue().size()];
			signerArray = entry.getValue().toArray(signerArray);
			signLineMap.get(entry.getKey()).setSignerList(signerArray);
		}
		// SignLine
		signLineArray = new SignLine[signLineMap.values().size()];
		signLineArray = signLineMap.values().toArray(signLineArray);
		request.setSignLineList(signLineArray);
		// Document
		documentArray = new Document[documentList.size()];
		documentArray = documentList.toArray(documentArray);
		request.setDocumentList(documentArray);
		// Request
		requestId = wsModifyService.createRequest(authentication, request);
		wsModifyService.sendRequest(authentication, new StringHolder(requestId));
		clearRequest();
		return requestId;
	}

	@Override
	public void addSignLine(final Integer signLine) {
		signLineMap.put(signLine, new SignLine());
	}

	@Override
	public void setSignLineType(final Integer signLine, final SignLineType signLineType) {
		signLineMap.get(signLine).setType(signLineType);
	}

	@Override
	public void addSigner(final Integer signLine, final String signer) {
		Signer defaultSigner = new Signer();
		User user1 = new User();
		user1.setIdentifier(signer);
		defaultSigner.setUserJob(user1);
		if (!signerMap.containsKey(signLine)) {
			signerMap.put(signLine, new LinkedList<Signer>());
		}
		signerMap.get(signLine).add(defaultSigner);
	}

	@Override
	public void deleteSigner(final Integer signLine, final String signer) {
		Integer index = signerMap.get(signLine).indexOf(signer);
		signerMap.get(signLine).remove(index);
	}

	@Override
	public void addDocument(final byte[] content, final String mimeType, final String name, final DocumentType documentType) throws ExceptionInfo, RemoteException {
		Document document = new Document();
		document.setContent(content);
		document.setMime(mimeType);
		document.setName(name);
		document.setDocumentType(documentType);
		document.setSign(true);
		documentList.add(document);
	}

	@Override
	public void deleteDocument(final String documentId, final String requestId)
			throws ExceptionInfo, RemoteException {
		if (null == request || null == request.getIdentifier()) {
			Request sentRequest = wsQueryService.queryRequest(authentication, requestId);
			Document[] documentList = sentRequest.getDocumentList();
			wsModifyService.deleteDocument(authentication, new StringHolder(documentId));
			if (documentList.length <= 1) {
				deleteRequest(requestId);
			}
		}
	}
	
	@Override
	public boolean existUser(final String userId) {
		try {
			wsQueryService.queryUsers(authentication, userId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	private void clearRequest() {
		request = null;
		signLineMap = new LinkedHashMap<Integer, SignLine>();
		signerMap = new LinkedHashMap<Integer, LinkedList<Signer>>();
		documentList = new LinkedList<Document>();
	}
	
	public byte[] getDocument(String documentId) throws IOException {
		wsQueryService.downloadDocument(authentication, documentId);
		InputStream is = XOPHandler.getDocumentStream();
		return IOUtils.toByteArray(is);
		
	}
	
	private byte[] getSign(String documentId) throws IOException {
		wsQueryService.downloadSign(authentication, documentId);
		InputStream is = XOPHandler.getDocumentStream();
		return IOUtils.toByteArray(is);
	}
}
