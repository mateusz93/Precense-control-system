package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class DifferentPasswordsException extends ValidationException {

    public DifferentPasswordsException(String messageCode) {
        super(messageCode);
    }
}
