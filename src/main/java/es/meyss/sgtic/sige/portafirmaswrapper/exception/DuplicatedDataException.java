package es.meyss.sgtic.sige.portafirmaswrapper.exception;

@SuppressWarnings("serial")
public class DuplicatedDataException extends WrapperException {
	public DuplicatedDataException(String field, String data) {
		super(field + " " + data + " already exists in the request");
	}
}
