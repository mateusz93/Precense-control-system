package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
public class ValidationException extends Exception {

    public ValidationException(String messageCode) {
        super(messageCode);
    }
}
