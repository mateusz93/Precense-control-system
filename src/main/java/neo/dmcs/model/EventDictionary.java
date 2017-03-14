package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "eventdictionary", schema = "data")
@Data
public class EventDictionary {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
