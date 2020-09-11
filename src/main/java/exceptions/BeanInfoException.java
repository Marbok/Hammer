package exceptions;

public class BeanInfoException extends RuntimeException {

    public BeanInfoException(String message) {
        super(message);
    }

    public BeanInfoException(String mess, Exception e) {
        super(mess, e);
    }
}
