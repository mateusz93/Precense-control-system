package neo.dmcs.repository;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.TeacherCourse;
import java.util.List;

public interface CourseDateRepository extends CrudRepository<CourseDate, Integer> {

    List<CourseDate> findByTeacherCourse(TeacherCourse teacherCourse);
}
