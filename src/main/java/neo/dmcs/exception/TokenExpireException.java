package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
public class TokenExpireException extends ValidationException {

    public TokenExpireException(String messageCode) {
        super(messageCode);
    }
}
