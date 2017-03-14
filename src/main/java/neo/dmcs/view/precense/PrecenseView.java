package neo.dmcs.view.precense;

import lombok.Data;
import neo.dmcs.model.TeacherCourse;
import java.util.Date;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Data
public class PrecenseView {

    private int id;
    private Date startTime;
    private Date finishTime;
    private Date date;
    private TeacherCourse courseID;
}
