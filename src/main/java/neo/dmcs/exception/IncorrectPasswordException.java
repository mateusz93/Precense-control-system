package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class IncorrectPasswordException extends ValidationException {

    public IncorrectPasswordException(String messageCode) {
        super(messageCode);
    }
}
