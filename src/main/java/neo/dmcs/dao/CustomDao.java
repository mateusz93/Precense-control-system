package neo.dmcs.dao;

import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;

import java.util.List;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
public interface CustomDao {

    List<TeacherPrecensesView> findTeacherPrecensesByUserId(int id);
    List<StudentPrecensesView> findStudentPrecensesByUserId(int id);

}
