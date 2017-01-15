package neo.dmcs.repository;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.StudentPrecense;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentPrecenseRepository extends JpaRepository<StudentPrecense, Integer> {

    List<StudentPrecense> findByStudent(User student);
    StudentPrecense findByCourseDate(CourseDate courseDate);
    StudentPrecense findByCourseDateAndStudent(CourseDate courseDate, User student);
}
