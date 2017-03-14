package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "applog", schema = "data")
@Data
public class AppLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "time")
    private Timestamp time;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventDictionaryID")
    private EventDictionary eventDictionary;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User student;

    @Column(name = "description")
    private String description;

}
