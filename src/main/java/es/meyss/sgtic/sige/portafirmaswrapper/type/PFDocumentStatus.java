package es.meyss.sgtic.sige.portafirmaswrapper.type;

public class PFDocumentStatus {
	private byte[] originalContent;
	private byte[] signedContent;
	private byte[] signReportContent;
	private PFStatusType status;

	public PFDocumentStatus() {
		originalContent = null;
		signedContent = null;
		signReportContent = null;
		status = null;
	}

	public byte[] getOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(byte[] originalContent) {
		this.originalContent = originalContent;
	}

	public byte[] getSignedContent() {
		return signedContent;
	}

	public void setSignedContent(byte[] signedContent) {
		this.signedContent = signedContent;
	}

	public byte[] getSignReportContent() {
		return signReportContent;
	}

	public void setSignReportContent(byte[] signReportContent) {
		this.signReportContent = signReportContent;
	}

	public PFStatusType getStatus() {
		return status;
	}

	public void setStatus(PFStatusType status) {
		this.status = status;
	}

}
