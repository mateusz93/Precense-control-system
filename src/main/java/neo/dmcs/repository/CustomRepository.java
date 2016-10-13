package neo.dmcs.repository;

import neo.dmcs.model.User;

import java.util.List;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
public interface CustomRepository extends CrudRepository<User, Integer> {

    List<Object[]> findTeacherPrecensesByUserId(int id);
    List<Object[]> findStudentPrecensesByUserId(int id);
    List<Object[]> findTeacherCoursesByUserId(int id);
    List<Object[]> findStudentCoursesByUserId(int id);
    List<Object[]> findTeacherCourses();

}
