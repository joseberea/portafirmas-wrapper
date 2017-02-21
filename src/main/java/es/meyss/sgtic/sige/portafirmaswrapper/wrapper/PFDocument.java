package es.meyss.sgtic.sige.portafirmaswrapper.wrapper;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.Document;

public class PFDocument extends Document {

	private static final long serialVersionUID = 1L;

	public PFDocument(Document document) {
		super(document.getIdentifier(), document.getName(), document.getMime(), document.getDocumentType(),
				document.getContent(), document.getType(), document.getUri(), document.getSign());
	}
}