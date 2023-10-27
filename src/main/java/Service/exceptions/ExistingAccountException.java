package Service.exceptions;

public class ExistingAccountException extends RuntimeException{
    
    public ExistingAccountException(String errorMessage) {
        super(errorMessage);
    }
    
}
