package todoapp.commons.exceptions;

public class InvalidChoiceException extends Throwable {
      public InvalidChoiceException(String invalid_choice) {
        super(invalid_choice);
    }
}
