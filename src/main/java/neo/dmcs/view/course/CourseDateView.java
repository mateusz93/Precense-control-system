package neo.dmcs.view.course;

import java.sql.Time;
import java.sql.Date;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
public class CourseDateView {

    private String status;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private int courseDateID;
    private String isEdited;
    private String dateId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public int getCourseDateID() {
        return courseDateID;
    }

    public void setCourseDateID(int courseDateID) {
        this.courseDateID = courseDateID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(String isEdited) {
        this.isEdited = isEdited;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDateView that = (CourseDateView) o;

        if (courseDateID != that.courseDateID) return false;
        if (!date.equals(that.date)) return false;
        if (!startTime.equals(that.startTime)) return false;
        return finishTime.equals(that.finishTime);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + finishTime.hashCode();
        result = 31 * result + courseDateID;
        return result;
    }
}
