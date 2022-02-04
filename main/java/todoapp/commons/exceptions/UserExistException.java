package todoapp.commons.exceptions;

public class UserExistException extends Throwable {
    public UserExistException(String s) {
        super(s);
    }
}
