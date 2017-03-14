package neo.dmcs.view.course;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
@Data
public class SubjectView {

    private String name;
    private String description;
    private String field;
    private int yearOfStudy;
    private int quantity;
    private int minQuantity;

}
