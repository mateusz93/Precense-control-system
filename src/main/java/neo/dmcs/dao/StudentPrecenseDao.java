package neo.dmcs.dao;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.StudentPrecense;
import neo.dmcs.model.User;

import java.util.List;

public interface StudentPrecenseDao extends GenericDao<StudentPrecense, Integer> {

    List<StudentPrecense> findByStudent(User student);
    StudentPrecense findByCourseDate(CourseDate courseDate);
    List<StudentPrecense> findAll();
}
