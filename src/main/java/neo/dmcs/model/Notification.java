package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author Mateusz Wieczorek on 10/22/16.
 */
@Entity
@Table(name = "notification", schema = "data")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "courseCanceled")
    private String courseCanceled;

    @Column(name = "changeCourseDate")
    private String changeCourseDate;

    @Column(name = "absence")
    private String absence;

    @Column(name = "criticalPresenceLevel")
    private String criticalPresenceLevel;

    @Column(name = "badMark")
    private String badMark;

}
