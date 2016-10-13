package neo.dmcs.repository;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.StudentPrecense;
import neo.dmcs.model.User;
import java.util.List;

public interface StudentPrecenseRepository extends CrudRepository<StudentPrecense, Integer> {

    List<StudentPrecense> findByStudent(User student);
    StudentPrecense findByCourseDate(CourseDate courseDate);
}
