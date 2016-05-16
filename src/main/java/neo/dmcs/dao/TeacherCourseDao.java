package neo.dmcs.dao;

import neo.dmcs.model.Subject;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;

import java.util.List;

public interface TeacherCourseDao extends GenericDao<TeacherCourse, Integer> {

    List<TeacherCourse> findBySubject(Subject subject);
    List<TeacherCourse> findByTeacher(User teacher);
    List<TeacherCourse> findAll();
}
