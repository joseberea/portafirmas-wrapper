package es.meyss.sgtic.sige.portafirmaswrapper.constant;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.Request;

public class PFSingleRequest {
	private Request request;
	private static PFSingleRequest instance;

    /**
     * A private Constructor prevents any other class from
     * instantiating.
     */
    private PFSingleRequest() {
        // nothing to do this time
    }

    /**
     * The Static initializer constructs the instance at class
     * loading time; this is to simulate a more involved
     * construction process (it it were really simple, you'd just
     * use an initializer)
     */
    static {
        instance = new PFSingleRequest();
    }

    /** Static 'instance' method */
    public static PFSingleRequest getInstance() {
        return instance;
    }

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}
