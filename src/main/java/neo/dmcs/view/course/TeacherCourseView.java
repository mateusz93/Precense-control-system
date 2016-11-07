package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
public class TeacherCourseView {

    private int ID;
    private String subjectName;
    private int coursesQuantity;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCoursesQuantity() {
        return coursesQuantity;
    }

    public void setCoursesQuantity(int coursesQuantity) {
        this.coursesQuantity = coursesQuantity;
    }
}
