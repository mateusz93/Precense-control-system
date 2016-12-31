package neo.dmcs.repository;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDateRepository extends JpaRepository<CourseDate, Integer> {

    List<CourseDate> findByTeacherCourse(TeacherCourse teacherCourse);
}
