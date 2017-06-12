package neo.dmcs.bpm.signal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Wieczorek on 31.05.2017.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Const {

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String VALIDATION_ERROR_CODE = "502";
    public static final String VALIDATION_TIMEOUT = "503";
    public static final String CORRECTED_DATA = "CORRECTED_DATA_Message";
    public static final String CONFIRMATION_TOKEN_Message = "CONFIRMATION_TOKEN_Message";
    public static final String TOKEN_TIMEOUT = "TOKEN_TIMEOUT";
    public static final String TOKEN_TIMEOUT_CODE = "504";
    public static final String PROCESS_STARTED = "PROCESS_STARTED_Message";
    public static final String PROCESS_FINISHED = "PROCESS_FINISHED_Message";

    public static final String PROCESS_ID = "Process_0w2p6p8";
}
