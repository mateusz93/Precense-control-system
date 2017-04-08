package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class IncorrectEmailException extends ValidationException {

    public IncorrectEmailException(String messageCode) {
        super(messageCode);
    }
}
