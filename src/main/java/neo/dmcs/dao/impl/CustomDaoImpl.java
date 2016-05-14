package neo.dmcs.dao.impl;

import neo.dmcs.dao.CustomDao;
import neo.dmcs.view.course.StudentCourseView;
import neo.dmcs.view.course.TeacherCourseView;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
@Transactional
@Repository
public class CustomDaoImpl implements CustomDao {

    private final String TeacherPrecensesByTeacherId = "SELECT TeacherCourse.ID as id, Subject.name AS subjectName, " +
            " Department.name AS departmentName, TeacherCourse.type, TeacherCourse.coursesQuantity as quantity FROM TeacherCourse" +
            " JOIN Subject ON Subject.ID = TeacherCourse.subjectID" +
            " JOIN Department ON Subject.departmentID = Department.ID" +
            " JOIN User ON User.ID = TeacherCourse.teacherID" +
            " WHERE User.ID =:userID";


    private final String StudentPrecensesByStudentId = "SELECT Subject.Name AS subjectName, Department.Name AS departmentName, " +
            "TeacherCourse.type, TeacherCourse.ID AS courseId, TeacherCourse.coursesQuantity AS quantity, " +
            "concat(User.firstName, ' ', User.lastName) AS teacherName FROM StudentCourse "+
            "JOIN TeacherCourse ON TeacherCourse.ID = StudentCourse.teacherCourseID "+
            "JOIN User ON User.ID = TeacherCourse.teacherID "+
            "JOIN Subject ON Subject.ID = TeacherCourse.subjectID "+
            "JOIN Department ON Department.ID = Subject.departmentID "+
            "WHERE StudentCourse.studentID =:userID";

    private final String StudentCoursesByStudentId = "SELECT Subject.Name AS subjectName, Department.Name AS departmentName, TeacherCourse.type, " +
            "TeacherCourse.ID AS courseID, TeacherCourse.coursesQuantity, concat(User.firstName, ' ', User.lastName) AS teacherName FROM StudentCourse " +
            "JOIN TeacherCourse ON TeacherCourse.ID=StudentCourse.teacherCourseID " +
            "JOIN User ON User.ID = TeacherCourse.teacherID " +
            "JOIN Subject ON Subject.ID = TeacherCourse.subjectID " +
            "JOIN Department ON Department.ID = Subject.departmentID " +
            "WHERE StudentCourse.studentID =:userID";

    private final String TeacherCoursesByTeacherId = "SELECT TeacherCourse.ID, Subject.Name AS subjectName, Department.Name AS departmentName, " +
            "TeacherCourse.type, TeacherCourse.coursesQuantity FROM TeacherCourse " +
            "JOIN Subject ON Subject.ID = TeacherCourse.subjectID " +
            "JOIN Department ON Subject.departmentID = Department.ID " +
            "JOIN User ON User.ID = TeacherCourse.teacherID " +
            "WHERE User.ID =:userID";

    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<Object[]> findTeacherPrecensesByUserId(int id) {
        return em.createNativeQuery(TeacherPrecensesByTeacherId).setParameter("userID", id).getResultList();
    }

    @Override
    public List<Object[]> findStudentPrecensesByUserId(int id) {
        return em.createNativeQuery(StudentPrecensesByStudentId).setParameter("userID", id).getResultList();
    }

    @Override
    public List<Object[]> findTeacherCoursesByUserId(int id) {
        return em.createNativeQuery(TeacherCoursesByTeacherId).setParameter("userID", id).getResultList();
    }

    @Override
    public List<Object[]> findStudentCoursesByUserId(int id) {
        return em.createNativeQuery(StudentCoursesByStudentId).setParameter("userID", id).getResultList();
    }
}
