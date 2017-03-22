package es.meyss.sgtic.sige.portafirmaswrapper.type;

import es.meyss.sgtic.sige.portafirmas.type.Document;

public class PFDocument extends Document {

	private static final long serialVersionUID = 1L;

	public PFDocument(Document document) {
		super(document.getIdentifier(), document.getName(), document.getMime(), document.getDocumentType(),
				document.getContent(), document.getType(), document.getUri(), document.getSign());
	}
}
