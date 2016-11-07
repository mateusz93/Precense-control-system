package neo.dmcs.view.precense;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
public class TeacherPrecensesView {

    private int id;
    private String subjectName;
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
