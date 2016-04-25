package neo.dmcs.dao;

import neo.dmcs.model.StudentCourse;
import java.util.List;

public interface StudentCourseDao extends GenericDao<StudentCourse, Integer> {

    List<StudentCourse> findByTeacherCourseId(int id);
    List<StudentCourse> findByStudentId(int id);
    List<StudentCourse> findAll();
}
