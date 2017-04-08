package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class EmailExistsException extends ValidationException {

    public EmailExistsException(String messageCode) {
        super(messageCode);
    }
}
