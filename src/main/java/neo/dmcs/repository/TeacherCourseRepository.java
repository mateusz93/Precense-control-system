package neo.dmcs.repository;

import neo.dmcs.model.Subject;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;

import java.util.List;

public interface TeacherCourseRepository extends CrudRepository<TeacherCourse, Integer> {

    List<TeacherCourse> findBySubject(Subject subject);
    TeacherCourse findBySubjectAndStudentGroup(Subject subject, String studentGroup);
    List<TeacherCourse> findByTeacher(User teacher);
}
