package neo.dmcs.view.course;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 19.05.16.
 */
@Data
public class NewCourseView {

    private String studentGroup;
    private String teacherLogin;
    private String subjectName;
    private String description;

}
