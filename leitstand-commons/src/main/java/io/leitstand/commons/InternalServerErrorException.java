package io.leitstand.commons;

public class InternalServerErrorException extends LeitstandException {

    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(Reason reason, Object... arguments) {
        super(reason, arguments);
    }

    
}
