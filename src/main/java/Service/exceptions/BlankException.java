package Service.exceptions;

public class BlankException extends RuntimeException {
    
    public BlankException (String errorMessage) {
        super(errorMessage);
    }
}
