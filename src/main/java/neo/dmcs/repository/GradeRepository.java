package neo.dmcs.repository;

import neo.dmcs.model.Grade;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    List<Grade> findByTeacherCourse(TeacherCourse teacherCourse);
    List<Grade> findByPreviousGrade(Grade previousGrade);
    List<Grade> findByValue(Integer value);
    List<Grade> findByUser(User user);
    List<Grade> findByTeacherCourseAndUser(TeacherCourse teacherCourse, User user);

}
