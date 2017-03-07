package es.meyss.sgtic.sige.portafirmaswrapper.exception;

@SuppressWarnings("serial")
public class NullRequestException extends WrapperException {
	public NullRequestException() {
		super("There is no active request");
	}
}
