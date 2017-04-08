package neo.dmcs.repository;

import neo.dmcs.model.CourseDate;
import neo.dmcs.model.StudentPresence;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentPresenceRepository extends JpaRepository<StudentPresence, Integer> {

    List<StudentPresence> findByStudent(User student);
    StudentPresence findByCourseDate(CourseDate courseDate);
    StudentPresence findByCourseDateAndStudent(CourseDate courseDate, User student);
}
