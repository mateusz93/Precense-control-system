package neo.dmcs.dao;

import neo.dmcs.model.TeacherCourse;
import java.util.List;

public interface TeacherCourseDao extends GenericDao<TeacherCourse, Integer> {

    List<TeacherCourse> findBySubjectId(int id);
    List<TeacherCourse> findByTeacherId(int id);
    List<TeacherCourse> findAll();
}
