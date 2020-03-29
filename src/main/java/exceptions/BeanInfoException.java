package exceptions;

public class BeanInfoException extends ClassNotFoundException {
    public BeanInfoException(String mess, Exception e) {
        super(mess, e);
    }
}
