package es.meyss.sgtic.sige.portafirmaswrapper.exception;

@SuppressWarnings("serial")
public class WrapperConfigException extends WrapperException {
	public WrapperConfigException(String reason) {
		super(reason + " configuration not found");
	}
}
