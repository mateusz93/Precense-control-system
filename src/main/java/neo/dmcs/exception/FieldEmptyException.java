package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class FieldEmptyException extends ValidationException {

    public FieldEmptyException(String messageCode) {
        super(messageCode);
    }
}
