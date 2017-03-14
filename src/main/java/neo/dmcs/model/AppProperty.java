package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */

@Entity
@Table(name = "appproperty", schema = "data")
@Data
public class AppProperty {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

}
