package neo.dmcs.view.course;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
public class SeveralCourseDateView {

    private String date;
    private String startTime;
    private String finishTime;
    private String firstDate;
    private String lastDate;
    private int space;
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

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getCourseDateID() {
        return courseDateID;
    }

    public void setCourseDateID(int courseDateID) {
        this.courseDateID = courseDateID;
    }
}
