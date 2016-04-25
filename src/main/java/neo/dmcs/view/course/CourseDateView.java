package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
public class CourseDateView {

    private String date;
    private String startTime;
    private String finishTime;
    private int courseDateID;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getCourseDateID() {
        return courseDateID;
    }

    public void setCourseDateID(int courseDateID) {
        this.courseDateID = courseDateID;
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
