package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
public class CourseView {

    private int id;
    private String subjectName;
    private String departmentID;
    private String type;
    private int quantity;
    private int min;
    private String teacherName;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseView that = (CourseView) o;

        if (id != that.id) return false;
        if (quantity != that.quantity) return false;
        if (min != that.min) return false;
        if (!subjectName.equals(that.subjectName)) return false;
        if (!departmentID.equals(that.departmentID)) return false;
        if (!type.equals(that.type)) return false;
        if (!teacherName.equals(that.teacherName)) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subjectName.hashCode();
        result = 31 * result + departmentID.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + min;
        result = 31 * result + teacherName.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

}
