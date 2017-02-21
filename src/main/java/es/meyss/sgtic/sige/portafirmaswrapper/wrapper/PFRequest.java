package es.meyss.sgtic.sige.portafirmaswrapper.wrapper;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.Request;

public class PFRequest extends Request {

	private static final long serialVersionUID = 1L;

	public PFRequest() {
		super();
	}

	public PFRequest(Request request) {
		super(request.getIdentifier(), 
				request.getSubject(), 
				request.getFentry(), 
				request.getFstart(), 
				request.getFexpiration(), 
				request.getReference(), 
				request.getText(), 
				request.getSignType(), 
				request.getApplication(), 
				request.getImportanceLevel(), 
				request.getDocumentList(), 
				request.getSignLineList(), 
				request.getRemitterList(), 
				request.getParameterList(), 
				request.getNoticeList(), 
				request.getActionList(), 
				request.getCommentList(), 
				request.getRequestStatus(), 
				request.getTimestampInfo(), 
				request.getEmailToNotifyList());
	}
}
