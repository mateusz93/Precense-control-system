package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class IncorrectUserTypeException extends ValidationException {

    public IncorrectUserTypeException(String messageCode) {
        super(messageCode);
    }

}
