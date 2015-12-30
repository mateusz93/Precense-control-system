
package model;

import db.TeacherCourses;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Mateusz Wieczorek
 * 
 */
public class StudentPresence {

    private int id;
    private Date startTime;
    private Date finishTime;
    private Date date;
    private TeacherCourses courseID;
    
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

    public TeacherCourses getCourseID() {
        return courseID;
    }

    public void setCourseID(TeacherCourses courseID) {
        this.courseID = courseID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.startTime);
        hash = 79 * hash + Objects.hashCode(this.finishTime);
        hash = 79 * hash + Objects.hashCode(this.date);
        hash = 79 * hash + Objects.hashCode(this.courseID);
        hash = 79 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentPresence other = (StudentPresence) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.finishTime, other.finishTime)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.courseID, other.courseID)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }
    private String status;

    @Override
    public String toString() {
        return "StudentPresence{" + "id=" + id + ", startTime=" + startTime + ", finishTime=" + finishTime + ", date=" + date + ", courseID=" + courseID + ", status=" + status + '}';
    }
}
