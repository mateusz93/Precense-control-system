package neo.dmcs.dao;

import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;

import java.util.List;

public interface StudentCourseDao extends GenericDao<StudentCourse, Integer> {

    List<StudentCourse> findByTeacherCourse(TeacherCourse teacherCourse);
    List<StudentCourse> findByStudent(User student);
    List<StudentCourse> findByStudentAndTeacherCourse(User student, TeacherCourse teacherCourse);
    List<StudentCourse> findAll();
}
