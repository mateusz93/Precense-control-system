package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
public class TokenIncorrectException extends ValidationException {

    public TokenIncorrectException(String messageCode) {
        super(messageCode);
    }
}
