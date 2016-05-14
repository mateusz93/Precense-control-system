package neo.dmcs.dao.impl;

import neo.dmcs.dao.CustomDao;
import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;
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


    @PersistenceContext(name = "data")
    private EntityManager em;

    @Override
    public List<TeacherPrecensesView> findTeacherPrecensesByUserId(int id) {
        return em.createNativeQuery(TeacherPrecensesByTeacherId).setParameter("userID", id).getResultList();
    }

    @Override
    public List<StudentPrecensesView> findStudentPrecensesByUserId(int id) {
        return em.createNativeQuery(StudentPrecensesByStudentId).setParameter("userID", id).getResultList();
    }
}
