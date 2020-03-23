package exceptions;

public class ContextException extends RuntimeException {
    public ContextException(String mess) {
        super(mess);
    }

    public ContextException(String mess, Exception e) {
        super(mess, e);
    }
}
