package todoapp.commons.exceptions;

public class UserNotExistException extends Throwable{
    public UserNotExistException(String s) {
        super(s);
    }
}
