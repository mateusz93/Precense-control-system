package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
public class SaveView {

    private String subjectName;
    private String departmentName;
    private String type;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!departmentName.equals(saveView.departmentName)) return false;
        if (!type.equals(saveView.type)) return false;
        if (!teacherName.equals(saveView.teacherName)) return false;
        return description.equals(saveView.description);

    }

    @Override
    public int hashCode() {
        int result = subjectName.hashCode();
        result = 31 * result + departmentName.hashCode();
        result = 31 * result + type.hashCode();
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
                ", departmentName='" + departmentName + '\'' +
                ", type='" + type + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", description='" + description + '\'' +
                ", coursesQuantity=" + coursesQuantity +
                ", id=" + id +
                '}';
    }
}
