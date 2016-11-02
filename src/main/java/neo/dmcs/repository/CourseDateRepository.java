package neo.dmcs.repository;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.Subject;
import java.util.List;

public interface CourseDateRepository extends CrudRepository<CourseDate, Integer> {

    List<CourseDate> findBySubject(Subject subject);
}
