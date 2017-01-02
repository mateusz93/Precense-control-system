package neo.dmcs.model;

import javax.persistence.*;

/**
 * @Author Mateusz Wieczorek on 10/22/16.
 */
@Entity
@Table(name = "notification", schema = "data")
public class Notification {

    private int id;
    private User user;
    private String courseCanceled;
    private String changeCourseDate;
    private String absence;
    private String criticalPresenceLevel;
    private String badMark;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "courseCanceled")
    public String getCourseCanceled() {
        return courseCanceled;
    }

    public void setCourseCanceled(String courseCanceled) {
        this.courseCanceled = courseCanceled;
    }

    @Column(name = "changeCourseDate")
    public String getChangeCourseDate() {
        return changeCourseDate;
    }

    public void setChangeCourseDate(String changeCourseDate) {
        this.changeCourseDate = changeCourseDate;
    }

    @Column(name = "absence")
    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    @Column(name = "criticalPresenceLevel")
    public String getCriticalPresenceLevel() {
        return criticalPresenceLevel;
    }

    public void setCriticalPresenceLevel(String criticalPresenceLevel) {
        this.criticalPresenceLevel = criticalPresenceLevel;
    }

    @Column(name = "badMark")
    public String getBadMark() {
        return badMark;
    }

    public void setBadMark(String badMark) {
        this.badMark = badMark;
    }
}
