package es.meyss.sgtic.sige.portafirmaswrapper.wrapper;

import java.rmi.RemoteException;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.DocumentType;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.ExceptionInfo;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.ImportanceLevel;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.SignLineType;
import es.meyss.sgtic.sige.portafirmas.client.ws.type.SignType;

public interface IPortafirmasWrapper {
	/**
	 * Crear request
	 */
	public void createRequest() throws ExceptionInfo, RemoteException;

	public void createRequest(final String subject, final ImportanceLevel importanceLevel, SignType signType) throws ExceptionInfo, RemoteException;

	public void setRequestSubject(final String subject);

	public void setRequestImportanceLevel(final ImportanceLevel importanceLevel);

	public void setRequestSignType(final SignType signType);
	
	public PFResponse getRequest(String requestId) throws ExceptionInfo, RemoteException;

	public void deleteRequest(final String requestId) throws ExceptionInfo, RemoteException;

	public String sendRequest() throws ExceptionInfo, RemoteException;

	public void addSignLine(final Integer signLine);

	public void setSignLineType(final Integer signLine, final SignLineType signLineType);

	public void addSigner(final Integer signLine, final String signer);

	public void deleteSigner(final Integer signLine, final String signer);
	
	public void addDocument(final byte[] content, final String mimeType, final String name, final DocumentType documentType) throws ExceptionInfo, RemoteException ;
	
	public void deleteDocument(final String documentId, final String requestId) throws ExceptionInfo, RemoteException ;
}
