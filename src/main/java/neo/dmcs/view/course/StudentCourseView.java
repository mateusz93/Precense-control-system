package neo.dmcs.view.course;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Data
public class StudentCourseView {

    private int courseId;
    private String name;
    private String description;
    private int coursesQuantity;
    private String teacherName;

}
