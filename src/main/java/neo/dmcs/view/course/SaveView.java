package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
public class SaveView {

    private String subjectName;
    private String teacherName;
    private String description;
    private int coursesQuantity;
    private int id;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaveView saveView = (SaveView) o;

        if (coursesQuantity != saveView.coursesQuantity) return false;
        if (id != saveView.id) return false;
        if (!subjectName.equals(saveView.subjectName)) return false;
        if (!teacherName.equals(saveView.teacherName)) return false;
        return description.equals(saveView.description);

    }

    @Override
    public int hashCode() {
        int result = subjectName.hashCode();
        result = 31 * result + teacherName.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + coursesQuantity;
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "SaveView{" +
                "subjectName='" + subjectName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", description='" + description + '\'' +
                ", coursesQuantity=" + coursesQuantity +
                ", id=" + id +
                '}';
    }
}
