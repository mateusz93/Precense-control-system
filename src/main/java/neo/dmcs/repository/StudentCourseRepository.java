package neo.dmcs.repository;

import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.Subject;
import neo.dmcs.model.User;

import java.util.List;

public interface StudentCourseRepository extends CrudRepository<StudentCourse, Integer> {

    List<StudentCourse> findBySubject(Subject subject);
    List<StudentCourse> findByStudent(User student);
    StudentCourse findByStudentAndSubject(User student, Subject subject);

}
