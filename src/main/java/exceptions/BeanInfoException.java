package exceptions;

public class BeanInfoException extends Exception {
    public BeanInfoException(String mess, Exception e) {
        super(mess, e);
    }
}
