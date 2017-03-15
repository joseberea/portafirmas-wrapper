package es.meyss.sgtic.sige.portafirmaswrapper.type;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.RequestStatus;

public final class PFStatusType {
	public static final RequestStatus ACEPTADO = RequestStatus.value1;
    public static final RequestStatus RECHAZADO = RequestStatus.value2;
    public static final RequestStatus CADUCADO = RequestStatus.value3;
    public static final RequestStatus EN_PROCESO = RequestStatus.value4;
    public static final RequestStatus RETIRADO = RequestStatus.value5;
    
    private String statusValue;
    
    public PFStatusType(String statusValue) {
    	this.statusValue = statusValue;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
}
