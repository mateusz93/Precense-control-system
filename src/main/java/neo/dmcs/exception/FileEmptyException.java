package neo.dmcs.exception;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
public class FileEmptyException extends ValidationException {

    public FileEmptyException(String messageCode) {
        super(messageCode);
    }
}
