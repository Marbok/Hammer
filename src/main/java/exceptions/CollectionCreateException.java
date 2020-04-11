package exceptions;

public class CollectionCreateException extends Exception {
    public CollectionCreateException(String mess, Exception e) {
        super(mess, e);
    }
}
