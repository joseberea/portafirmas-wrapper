package es.meyss.sgtic.sige.portafirmaswrapper.type;

public class PFDocumentStatus {
	private byte[] content;
	private PFStatusType status;

	public PFDocumentStatus() {
		content = null;
		status = null;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public PFStatusType getStatus() {
		return status;
	}

	public void setStatus(PFStatusType status) {
		this.status = status;
	}
	
}
