package es.meyss.sgtic.sige.portafirmaswrapper.exception;

@SuppressWarnings("serial")
public class MandatoryEmptyFieldException extends WrapperException {
	public MandatoryEmptyFieldException(String field) {
		super(field + " is a mandatory field");
	}
	
	public MandatoryEmptyFieldException(String field, String superField) {
		super(field + " of " + superField + " is a mandatory field");
	}
	
	public MandatoryEmptyFieldException(String field, String superField, int index) {
		super(field + " of " + superField + " #" + index + " is a mandatory field");
	}
}
