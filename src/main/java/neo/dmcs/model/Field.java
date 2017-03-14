package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author Mateusz Wieczorek on 10/29/16.
 */
@Entity
@Table(name = "field", schema = "data")
@Data
public class Field {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

}
