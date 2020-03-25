package exceptions;

public class CreateBeanException extends RuntimeException {

    public CreateBeanException(String mess, Exception e) {
        super(mess, e);
    }

    public CreateBeanException(String mess) {
        super(mess);
    }
}
