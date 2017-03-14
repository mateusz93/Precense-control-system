package neo.dmcs.view.user;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
@Data
public class ProfileNotificationView {

    private String courseCanceledEmail;
    private String courseCanceledSMS;
    private String changeCourseDateEmail;
    private String changeCourseDateSMS;
    private String absenceEmail;
    private String absenceSMS;
    private String criticalPresenceLevelEmail;
    private String criticalPresenceLevelSMS;
    private String badMarkEmail;
    private String badMarkSMS;

}
