package es.meyss.sgtic.sige.portafirmaswrapper.wrapper;

import java.io.IOException;
import java.rmi.RemoteException;

import es.meyss.sgtic.sige.portafirmas.type.DocumentType;
import es.meyss.sgtic.sige.portafirmas.type.ExceptionInfo;
import es.meyss.sgtic.sige.portafirmas.type.ImportanceLevel;
import es.meyss.sgtic.sige.portafirmas.type.SignLineType;
import es.meyss.sgtic.sige.portafirmas.type.SignType;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.DuplicatedDataException;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.MandatoryEmptyFieldException;
import es.meyss.sgtic.sige.portafirmaswrapper.exception.NullRequestException;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFDocumentStatus;
import es.meyss.sgtic.sige.portafirmaswrapper.type.PFResponse;

public interface IPortafirmasWrapper {

	public void createRequest();

	public void createRequest(final String subject, final ImportanceLevel importanceLevel, SignType signType, boolean longTimeSing) throws MandatoryEmptyFieldException;

	public void setRequestSubject(final String subject);

	public void setRequestImportanceLevel(final ImportanceLevel importanceLevel);

	public void setRequestSignType(final SignType signType);
	
	public PFResponse getRequest(String requestId) throws ExceptionInfo, RemoteException;
	
	public PFDocumentStatus getDocumentStatus(String documentId, String requestId) throws ExceptionInfo, RemoteException, IOException;
	
	public byte[] getOriginalDocument(String documentId) throws IOException;
	
	public byte[] getSignedDocument(String documentId) throws IOException;
	
	public byte[] getSignReport(String documentId) throws IOException;

	public void deleteRequest(final String requestId) throws ExceptionInfo, RemoteException;

	public String sendRequest() throws ExceptionInfo, RemoteException, MandatoryEmptyFieldException, NullRequestException;

	public void addSignLine(final Integer signLine) throws DuplicatedDataException;

	public void setSignLineType(final Integer signLine, final SignLineType signLineType);

	public void addSigner(final Integer signLine, final String signer) throws DuplicatedDataException;

	public void deleteSigner(final Integer signLine, final String signer);
	
	public void addDocument(final byte[] content, final String mimeType, final String name, final DocumentType documentType) throws ExceptionInfo, RemoteException ;
	
	public void deleteDocument(final String documentId, final String requestId) throws ExceptionInfo, RemoteException ;
	
	public boolean existUser(final String userId);
}
