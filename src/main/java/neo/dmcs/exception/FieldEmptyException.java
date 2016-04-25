package neo.dmcs.exception;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
public class FieldEmptyException extends Exception {
    public FieldEmptyException(String email) {
        super(email);
    }
}
