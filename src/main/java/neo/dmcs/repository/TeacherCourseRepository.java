package neo.dmcs.repository;

import neo.dmcs.model.Subject;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Integer> {

    List<TeacherCourse> findBySubject(Subject subject);
    TeacherCourse findBySubjectAndStudentGroup(Subject subject, String studentGroup);
    List<TeacherCourse> findByTeacher(User teacher);
}
