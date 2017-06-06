package neo.dmcs.exception;

import lombok.Getter;

/**
 * @author Mateusz Wieczorek, 09.04.16.
 */
@Getter
public class FieldEmptyException extends ValidationException {

    private String fieldName;

    public FieldEmptyException(String messageCode) {
        super(messageCode);
    }

    public FieldEmptyException(String messageCode, String fieldName) {
        super(messageCode);
        this.fieldName = fieldName;
    }
}
