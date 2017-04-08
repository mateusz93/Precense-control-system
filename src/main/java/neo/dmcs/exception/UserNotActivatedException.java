package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
public class UserNotActivatedException extends ValidationException {

    public UserNotActivatedException(String messageCode){
        super(messageCode);
    }
}
