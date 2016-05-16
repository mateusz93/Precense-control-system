package neo.dmcs.dao;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.TeacherCourse;

import java.util.List;

public interface CourseDateDao extends GenericDao<CourseDate, Integer> {

    List<CourseDate> findByTeacherCourse(TeacherCourse teacherCourse);
    List<CourseDate> findAll();
}
