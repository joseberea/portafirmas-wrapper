package es.meyss.sgtic.sige.portafirmaswrapper.exception;

@SuppressWarnings("serial")
public abstract class WrapperException extends Exception {
	/**
     * Construye una instancia de <code>ServiceException</code>.
     */
    public WrapperException() {
        super();
    }

    /**
     * Construye una instancia de <code>ServiceException</code> con el 
     * mensaje especificado del detalle del error.
     * @param message El mensaje del detalle del error.
     */
    public WrapperException(String message) {
        super(message);
    }
    
    /**
     * Construye una instancia de <code>ServiceException</code> con la causa del error.
     * @param cause La causa del error.
     */
    public WrapperException(Throwable cause) {
        super(cause);
    }
  
    /**
     * Construye una instancia de <code>ServiceException</code> con el mensaje 
     * especificado del detalle del error y la causa del mismo.
     * @param message El mensaje del detalle del error.
     * @param cause La causa del error.
     */
    public WrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
