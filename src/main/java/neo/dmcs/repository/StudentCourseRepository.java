package neo.dmcs.repository;

import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;

import java.util.List;

public interface StudentCourseRepository extends CrudRepository<StudentCourse, Integer> {

    List<StudentCourse> findByTeacherCourse(TeacherCourse teacherCourse);
    List<StudentCourse> findByStudent(User student);
    StudentCourse findByStudentAndTeacherCourse(User student, TeacherCourse teacherCourse);

}
