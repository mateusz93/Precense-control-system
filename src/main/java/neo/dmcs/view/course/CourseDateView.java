package neo.dmcs.view.course;

import lombok.Data;

import java.sql.Time;
import java.sql.Date;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
@Data
public class CourseDateView {

    private String status;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private int courseDateID;
    private String isEdited;
    private String dateId;

}
