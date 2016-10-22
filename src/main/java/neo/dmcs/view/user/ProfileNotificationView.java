package neo.dmcs.view.user;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
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

    public String getCourseCanceledEmail() {
        return courseCanceledEmail;
    }

    public void setCourseCanceledEmail(String courseCanceledEmail) {
        this.courseCanceledEmail = courseCanceledEmail;
    }

    public String getCourseCanceledSMS() {
        return courseCanceledSMS;
    }

    public void setCourseCanceledSMS(String courseCanceledSMS) {
        this.courseCanceledSMS = courseCanceledSMS;
    }

    public String getChangeCourseDateEmail() {
        return changeCourseDateEmail;
    }

    public void setChangeCourseDateEmail(String changeCourseDateEmail) {
        this.changeCourseDateEmail = changeCourseDateEmail;
    }

    public String getChangeCourseDateSMS() {
        return changeCourseDateSMS;
    }

    public void setChangeCourseDateSMS(String changeCourseDateSMS) {
        this.changeCourseDateSMS = changeCourseDateSMS;
    }

    public String getAbsenceEmail() {
        return absenceEmail;
    }

    public void setAbsenceEmail(String absenceEmail) {
        this.absenceEmail = absenceEmail;
    }

    public String getAbsenceSMS() {
        return absenceSMS;
    }

    public void setAbsenceSMS(String absenceSMS) {
        this.absenceSMS = absenceSMS;
    }

    public String getCriticalPresenceLevelEmail() {
        return criticalPresenceLevelEmail;
    }

    public void setCriticalPresenceLevelEmail(String criticalPresenceLevelEmail) {
        this.criticalPresenceLevelEmail = criticalPresenceLevelEmail;
    }

    public String getCriticalPresenceLevelSMS() {
        return criticalPresenceLevelSMS;
    }

    public void setCriticalPresenceLevelSMS(String criticalPresenceLevelSMS) {
        this.criticalPresenceLevelSMS = criticalPresenceLevelSMS;
    }

    public String getBadMarkEmail() {
        return badMarkEmail;
    }

    public void setBadMarkEmail(String badMarkEmail) {
        this.badMarkEmail = badMarkEmail;
    }

    public String getBadMarkSMS() {
        return badMarkSMS;
    }

    public void setBadMarkSMS(String badMarkSMS) {
        this.badMarkSMS = badMarkSMS;
    }
}
