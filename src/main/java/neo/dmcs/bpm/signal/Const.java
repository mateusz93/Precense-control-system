package neo.dmcs.bpm.signal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Wieczorek on 31.05.2017.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Const {

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String MAIN_VALIDATION_ERROR = "MAIN_VALIDATION_ERROR";
    public static final String CORRECT_EMAIL_Message = "CORRECT_EMAIL_Message";
    public static final String START_PROCESS_Message = "START_PROCESS_Message";
    public static final String TIMEOUT_PROCESS = "TIMEOUT";
    public static final String PROCESS_FINISHED_Message = "PROCESS_FINISHED_Message";
    public static final String SEND_TOKEN_Message = "SEND_TOKEN_Message";
    public static final String EMAIL_USED_Message = "EMAIL_USED_Message";
    public static final String INCORRECT_EMAIL_Message = "INCORRECT_EMAIL_Message";
}
