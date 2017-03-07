package es.meyss.sgtic.sige.portafirmaswrapper.type;

import java.util.LinkedHashMap;
import java.util.Map;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.Request;

public class PFResponse {

	PFRequest request;

	Map<String, PFDocument> documents = new LinkedHashMap<String, PFDocument>();

	public PFRequest getRequest() {
		return request;
	}

	public void setRequest(PFRequest request) {
		this.request = request;
	}
	
	public void setRequest(Request request) {
		this.request = new PFRequest(request);
	}

	public Map<String, PFDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(Map<String, PFDocument> documents) {
		this.documents = documents;
	}
}
