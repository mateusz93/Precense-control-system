package neo.dmcs.dao;

import neo.dmcs.model.CourseDate;
import java.util.List;

public interface CourseDateDao extends GenericDao<CourseDate, Integer> {

    List<CourseDate> findByCourseId(int id);
    List<CourseDate> findAll();
}
