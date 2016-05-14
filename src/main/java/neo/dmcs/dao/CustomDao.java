package neo.dmcs.dao;

import java.util.List;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
public interface CustomDao {

    List<Object[]> findTeacherPrecensesByUserId(int id);
    List<Object[]> findStudentPrecensesByUserId(int id);
    List<Object[]> findTeacherCoursesByUserId(int id);
    List<Object[]> findStudentCoursesByUserId(int id);
    List<Object[]> findTeacherCourses();

}
