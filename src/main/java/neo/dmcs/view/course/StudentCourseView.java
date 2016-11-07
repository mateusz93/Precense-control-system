package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
public class StudentCourseView {

    private int subjectID;
    private String name;
    private String description;
    private int coursesQuantity;
    private int minCoursesQuantity;
    private String teacherName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoursesQuantity() {
        return coursesQuantity;
    }

    public void setCoursesQuantity(int coursesQuantity) {
        this.coursesQuantity = coursesQuantity;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getMinCoursesQuantity() {
        return minCoursesQuantity;
    }

    public void setMinCoursesQuantity(int minCoursesQuantity) {
        this.minCoursesQuantity = minCoursesQuantity;
    }
}
