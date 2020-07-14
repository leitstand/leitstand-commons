package io.leitstand.commons;

/**
 * The <code>ServiceUnavailableException</code> indicates that a service is currently not available.
 */
public class ServiceUnavailableException extends LeitstandException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a <code>ServiceUnavailableException</code>.
     * @param reason the reason why the service is temporary not available.
     * @param args message template parameters.
     */
    public ServiceUnavailableException(Reason reason, Object... args){
        super(reason,args);
    }
    
}
