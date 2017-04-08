package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
public class IncorrectCaptchaException extends ValidationException {

    public IncorrectCaptchaException(String messageCode) {
        super(messageCode);
    }
}
