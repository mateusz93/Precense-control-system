package neo.dmcs.view.precense;

import neo.dmcs.model.TeacherCourse;
import java.util.Date;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
public class PrecenseView {

    private int id;
    private Date startTime;
    private Date finishTime;
    private Date date;
    private TeacherCourse courseID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TeacherCourse getCourseID() {
        return courseID;
    }

    public void setCourseID(TeacherCourse courseID) {
        this.courseID = courseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrecenseView that = (PrecenseView) o;

        if (id != that.id) return false;
        if (!startTime.equals(that.startTime)) return false;
        if (!finishTime.equals(that.finishTime)) return false;
        if (!date.equals(that.date)) return false;
        return courseID.equals(that.courseID);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + startTime.hashCode();
        result = 31 * result + finishTime.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + courseID.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PrecenseView{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", date=" + date +
                ", courseID=" + courseID +
                '}';
    }
}
